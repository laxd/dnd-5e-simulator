package uk.laxd.dndSimulator.character

import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.equipment.Weapon
import uk.laxd.dndSimulator.equipment.UnarmedAttack
import uk.laxd.dndSimulator.action.Damage
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.equipment.Armour
import uk.laxd.dndSimulator.event.DamageEvent
import uk.laxd.dndSimulator.event.EventLogger
import uk.laxd.dndSimulator.feature.Effect
import uk.laxd.dndSimulator.feature.Feature
import uk.laxd.dndSimulator.proficiency.Proficiency
import uk.laxd.dndSimulator.proficiency.ProficiencyAble
import java.util.*
import java.util.stream.Collectors
import kotlin.math.min

class Character(
    val name: String,
    val team: String
) {
    var hp = 0
    var maxHp = 0
    val characterClassLevels: MutableMap<CharacterClass, Int> = EnumMap(CharacterClass::class.java)
    val abilities: MutableMap<Ability, Int> = EnumMap(Ability::class.java)
    var attackCount = 1
    var armorClass = 10
    var proficiencyBonus: Int = 1
    var initiativeModifier: Int = 0
    val features: MutableList<Feature> = mutableListOf()
    val proficiencies: MutableList<Proficiency> = mutableListOf()

    // TODO: Combine armour and weapons into equipment list?
    val armour: MutableList<Armour> = mutableListOf()
    var weapons: MutableList<Weapon> = mutableListOf(UnarmedAttack())

    fun addLevel(characterClass: CharacterClass, level: Int) {
        characterClassLevels.compute(characterClass) { _: CharacterClass?, v: Int? -> if (v == null) level else level + v }
    }

    fun getLevel(characterClass: CharacterClass): Int {
        return characterClassLevels[characterClass] ?: 0
    }

    val level: Int
        get() = characterClassLevels.values
            .stream().mapToInt { i -> i }
            .sum()

    fun setAbilityScore(ability: Ability, score: Int) {
        abilities[ability] = score
    }

    fun getAbilityScore(ability: Ability): Int {
        return abilities[ability] ?: 10
    }

    fun getAbilityModifier(ability: Ability): Int {
        return Math.floor(((abilities[ability] ?: 10) - 10) / 2.0).toInt()
    }

    fun addFeature(feature: Feature) {
        features.add(feature)
    }

    fun applyDamage(causedBy: Character, causedByEffect: Effect, damage: Damage) {
        EventLogger.instance.logEvent(DamageEvent(causedBy, this, damage, causedByEffect))

        val totalDamage = min(damage.getTotalDamage(), hp)
        hp -= totalDamage
    }

    fun isAlive() = hp > 0
    fun isDead() = !isAlive()

    val characterClasses: Collection<CharacterClass>
        get() = characterClassLevels.keys
    val hitDice: Collection<Die>
        get() = characterClassLevels.entries.stream()
            .flatMap { e: Map.Entry<CharacterClass, Int> -> Collections.nCopies(e.value, e.key.hitDie).stream() }
            .collect(Collectors.toList())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Character) return false
        return name == other.name
    }

    override fun hashCode(): Int {
        return Objects.hash(name)
    }

    override fun toString(): String {
        return "$name ($hp/$maxHp)"
    }

    fun hasProficiency(proficiencyAble: ProficiencyAble): Boolean {
        val proficiencyNames = proficiencyAble.getProficiencyNames()

        return proficiencies.map { p -> p.name }
            .any { name -> proficiencyNames.contains(name) }
    }
}