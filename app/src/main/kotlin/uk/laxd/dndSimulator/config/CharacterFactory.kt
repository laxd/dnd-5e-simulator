package uk.laxd.dndSimulator.config

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.feature.FeatureFactory
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.Die
import java.util.stream.Collectors

@Component
class CharacterFactory(private val featureFactory: FeatureFactory) {

    fun createCharacters(characterConfigs: Collection<CharacterConfig>) : List<Character> {
        return characterConfigs
            .stream()
            .map { characterConfig: CharacterConfig -> createCharacter(characterConfig) }
            .collect(Collectors.toList())
    }

    private fun createCharacter(characterConfig: CharacterConfig): Character {
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

        // TODO: Armour class should be able to be calculated through equipment/features
        character.armorClass = characterConfig.armourClass

        featureFactory.createFeatures(characterConfig)
            .forEach { f -> character.addFeature(f) }

        character.features
            .forEach { f -> f.onCreate(character) }

        character.proficiencyBonus = (1 + Math.ceil(character.level / 4.0)).toInt()
        character.initiativeModifier = character.getAbilityModifier(Ability.DEXTERITY)

        // TODO: Look through equipment, find the best armour we can wear calculate our armour class from that

        // TODO: Delegate to WeaponFactory instead of creating weapons in CharacterConfig
        character.weapons.addAll(characterConfig.weapons)

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