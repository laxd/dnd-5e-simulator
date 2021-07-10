package uk.laxd.dndSimulator.config

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.feature.FeatureFactory
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.config.internal.CharacterConfig
import uk.laxd.dndSimulator.config.internal.CustomArmourConfig
import uk.laxd.dndSimulator.config.internal.LookupArmourConfig
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.equipment.Armour
import java.util.stream.Collectors

@Component
class CharacterFactory(
    private val featureFactory: FeatureFactory,
    private val armourFactory: ArmourFactory,
    private val weaponFactory: WeaponFactory
) {

    fun createCharacters(characterConfigs: Collection<CharacterConfig>) : List<Character> {
        return characterConfigs
            .stream()
            .map { characterConfig: CharacterConfig -> createCharacter(characterConfig) }
            .collect(Collectors.toList())
    }

    fun createCharacter(characterConfig: CharacterConfig): Character {
        val character = Character(
            characterConfig.name,
            characterConfig.team
        )

        characterConfig.characterClassLevels.forEach { t, u -> character.addLevel(t, u) }

        characterConfig.abilityScores.forEach { (ability: Ability, score: Int) ->
            character.setAbilityScore(
                ability,
                score
            )
        }

        val hp = getHp(character, characterConfig)

        character.maxHp = hp
        character.hp = hp

        if(characterConfig.overrideArmourClass == null) {
            // TODO: Should this be calculated by the character? Doing it here means it can't change mid-combat
            // Build all of the armour we have equipped, and then
            // find some armour that we can wear and calculate AC from that
            val armour = characterConfig.armour.mapNotNull { a -> armourFactory.createArmour(a) }
                .filter { a -> a.requiredStrength < character.getAbilityScore(Ability.STRENGTH) }
                .filter { a -> a.armourClass != null }
                .minByOrNull { a -> a.priority }

            if(armour == null) {
                character.armorClass = 10
            }
            else {
                character.armorClass = armour.armourClass!! + armour.getAdditionalArmourClass(character)
            }
        }
        else {
            character.armorClass = characterConfig.overrideArmourClass
        }
        // TODO: Allow to equip a shield if we have one

        character.weapons.addAll(
            characterConfig.weapons.map { w -> weaponFactory.createWeapon(w) }
        )

        character.proficiencyBonus = (1 + Math.ceil(character.level / 4.0)).toInt()
        character.initiativeModifier = character.getAbilityModifier(Ability.DEXTERITY)

        // Finally, allow features to modify character
        featureFactory.createFeatures(characterConfig)
            .forEach { f -> character.addFeature(f) }

        character.features
            .forEach { f -> f.onCreate(character) }

        return character
    }

    private fun getHp(character: Character, characterConfig: CharacterConfig): Int {
        // If HP has been set, use that
        if (characterConfig.hp != 0) {
            return characterConfig.hp
        }

        // If not, calculate HP from stats, and set it
        return character.level * character.getAbilityModifier(Ability.CONSTITUTION) +
                character.hitDice.stream()
                    .mapToInt { d: Die -> d.maxValue / 2 + 1 }
                    .sum()
    }
}