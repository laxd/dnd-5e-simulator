package uk.laxd.dndSimulator.config.internal

import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.character.CharacterClass
import java.util.*

/**
 * Defines a [Character] in a configuration style, allowing for repeatable
 * instantiation of a single character from a set configuration.
 */
class CharacterConfig(
    val name: String,
    val team: String,
    val overrideArmourClass: Int? = null
) {
    var hp: Int = 1
    val characterClassLevels: MutableMap<CharacterClass, Int> = EnumMap(CharacterClass::class.java)
    val abilityScores: MutableMap<Ability, Int> = EnumMap(Ability::class.java)

    val weapons: MutableCollection<WeaponConfig> = mutableListOf()
    val armour: MutableCollection<ArmourConfig> = mutableListOf()

    init {
        Ability.values().forEach { a -> abilityScores[a] = 10 }
    }

    fun getLevel(characterClass: CharacterClass): Int {
        return characterClassLevels[characterClass] ?: 0
    }

    fun addLevel(level: Int, characterClass: CharacterClass) {
        characterClassLevels.compute(characterClass) { _: CharacterClass, v: Int? -> if (v == null) level else v + level }
    }

    val characterClasses: Collection<CharacterClass>
        get() = characterClassLevels.keys
}