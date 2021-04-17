package uk.laxd.dndSimulator.config

import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.equipment.UnarmedAttack
import uk.laxd.dndSimulator.equipment.Weapon
import java.util.*

/**
 * Defines a [Character] in a configuration style, allowing for repeatable
 * instantiation of a single character from a set configuration.
 */
class CharacterConfig {
    // TODO: Allow multi-classing
    var name: String = "Un-named character"
    var hp: Int = 1
    var armourClass: Int = 10
    val characterClassLevels: MutableMap<CharacterClass, Int> = EnumMap(CharacterClass::class.java)
    val abilityScores: MutableMap<Ability, Int> = EnumMap(Ability::class.java)

    // TODO: Change to WeaponConfig and allow building a weapon by type
    var weapon: Weapon = UnarmedAttack()

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