package uk.laxd.dndSimulator.config

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.feature.FeatureFactory
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.config.internal.CharacterConfig
import uk.laxd.dndSimulator.config.internal.CustomArmourConfig
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.equipment.Armour
import uk.laxd.dndSimulator.equipment.Equipment
import uk.laxd.dndSimulator.equipment.ItemFactory
import uk.laxd.dndSimulator.equipment.Weapon
import java.util.stream.Collectors

@Component
class CharacterFactory(
    private val featureFactory: FeatureFactory,
    private val itemFactory: ItemFactory
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

        character.inventory.addAll(
            characterConfig.inventory.mapNotNull { i -> itemFactory.createItem(i) }
        )

        // Set everything to be NOT equipped by default
        character.inventory
            .filterIsInstance<Equipment>()
            .forEach { i -> i.isEquipped = false }

        // Then equip just a single item of armour
        val armour = character.inventory
            .filterIsInstance<Armour>()
                // TODO: Allow equipping sub-optimal armour instead of just filtering them out
            .filter { a -> a.requiredStrength >= character.getAbilityScore(Ability.STRENGTH) }
            .filter { a -> character.hasProficiency(a) }
            .maxByOrNull { a -> (a.armourClass ?: 0) + a.getAdditionalArmourClass(character) }

        armour?.isEquipped = true

        // TODO: Equip weapons
        character.inventory
            .filterIsInstance<Weapon>()
            .sortedBy { w -> w.priority }

        if(characterConfig.overrideArmourClass != null) {
            character.overrideArmourClass = characterConfig.overrideArmourClass
        }

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