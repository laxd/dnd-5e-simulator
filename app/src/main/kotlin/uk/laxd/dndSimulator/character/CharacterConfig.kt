package uk.laxd.dndSimulator.character

import uk.laxd.dndSimulator.ability.Ability
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
    var hp: Short = 1
    var armourClass: Short = 10
    val characterClassLevels: MutableMap<CharacterClass, Int> = EnumMap(CharacterClass::class.java)
    val abilityScores: MutableMap<Ability, Int> = EnumMap(Ability::class.java)

    // TODO: Change to WeaponConfig and allow building a weapon by type
    var weapon: Weapon = UnarmedAttack()

    init {
        Ability.values().forEach { a -> abilityScores[a] = 10 }
    }

    fun getLevel(characterClass: CharacterClass): Int {
        return characterClassLevels[characterClass]!!
    }

    fun addLevel(level: Int, characterClass: CharacterClass) {
        characterClassLevels.compute(characterClass) { _: CharacterClass, v: Int? -> if (v == null) level else v + level }
    }

    val characterClasses: Collection<CharacterClass>
        get() = characterClassLevels.keys
}