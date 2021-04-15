package uk.laxd.dndSimulator.config.json

import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.character.CharacterConfig
import java.util.*

class JsonCharacterConfig {

    var name: String = "Un-named character"
    var hp: Int? = null
    var AC: Int? = null
    val levels: Map<String, Int> = mapOf()
    val abilities: Map<String, Int> = mapOf()

    fun toConfig() : CharacterConfig {
        val characterConfig = CharacterConfig()

        characterConfig.name = this.name
        characterConfig.hp = this.hp ?: 0
        characterConfig.armourClass = this.AC ?: 0

        characterConfig.characterClassLevels.putAll(
            this.levels.mapKeys { (k, _) -> CharacterClass.valueOf(k) }
        )

        characterConfig.abilityScores.putAll(
            this.abilities.mapKeys { (k, _) -> Ability.valueOf(k) }
        )

        return characterConfig
    }

}