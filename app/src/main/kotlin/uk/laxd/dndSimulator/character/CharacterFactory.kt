package uk.laxd.dndSimulator.character

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.feature.FeatureFactory
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.config.CharacterConfig
import uk.laxd.dndSimulator.dice.Die

@Component
class CharacterFactory(private val featureFactory: FeatureFactory) {

    fun createCharacter(characterConfig: CharacterConfig): Character {
        val character = Character(
            characterConfig.name,
            CharacterClass.BARBARIAN,
            characterConfig.getLevel(CharacterClass.BARBARIAN)
        )

        characterConfig.abilityScores.forEach { (ability: Ability, score: Int) ->
            character.setAbilityScore(
                ability,
                score
            )
        }

        val hp = getHp(character, characterConfig)

        character.maxHp = hp.toInt()
        character.hp = hp.toInt()
        character.armorClass = characterConfig.armourClass.toInt()

        featureFactory.createFeatures(characterConfig)
            .forEach { f -> character.addFeature(f) }

        character.features
            .forEach { f -> f.onCreate(character) }

        // TODO: Delegate to WeaponFactory
        character.weapon = characterConfig.weapon

        return character
    }

    private fun getHp(character: Character, characterConfig: CharacterConfig): Int {
        // If HP has been set, use that
        if (character.hp != 0) {
            return characterConfig.hp
        }

        // If not, calculate HP from stats, and set it
        return character.level * character.getAbilityModifier(Ability.CONSTITUTION) +
                character.hitDice.stream()
                    .mapToInt { d: Die -> d.maxValue / 2 + 1 }
                    .sum()
    }
}