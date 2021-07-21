package uk.laxd.dndSimulator.character

import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.action.AttackAction
import uk.laxd.dndSimulator.equipment.Weapon
import uk.laxd.dndSimulator.equipment.UnarmedAttack
import uk.laxd.dndSimulator.action.Damage
import uk.laxd.dndSimulator.action.InitiativeRoll
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.equipment.Armour
import uk.laxd.dndSimulator.equipment.Item
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
    var overrideArmourClass: Int? = null
    var proficiencyBonus: Int = 1
    var initiativeModifier: Int = 0

    // TODO: Update to Effect instead? And then add all of equipped inventory?
    val features: MutableList<Feature> = mutableListOf()
    val proficiencies: MutableList<Proficiency> = mutableListOf()

    val inventory: MutableList<Item> = mutableListOf()

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

    fun getArmourClass(): Int {
        if(overrideArmourClass != null) {
            return overrideArmourClass!!
        }

        val armour = getEquippedArmour()

        if(armour == null || !hasProficiency(armour)) {
            return 10
        }

        return (armour.armourClass ?: 0) + armour.getAdditionalArmourClass(this)
    }

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

    fun getEquippedArmour(): Armour? {
        return inventory.filterIsInstance<Armour>()
            .firstOrNull { a -> a.isEquipped }
    }

    fun getEquippedWeapons(): List<Weapon> {
        return inventory.filterIsInstance<Weapon>()
            .filter { w -> w.isEquipped }
    }

    // All Effect methods replicated in Character to make it easier to apply

    fun onCreate() = features.forEach{ f -> f.onCreate(this)}
    fun onCombatStart() = features.forEach{ f -> f.onCombatStart(this)}
    fun onInitiativeRoll(initiativeRoll: InitiativeRoll) = features.forEach{ f -> f.onInitiativeRoll(initiativeRoll)}
    fun onRoundStart() = features.forEach{ f -> f.onRoundStart(this)}
    fun onTurnStart() = features.forEach{ f -> f.onTurnStart(this)}
    fun onTurnEnd() = features.forEach{ f -> f.onTurnEnd(this)}
    fun onAttackRoll(action: AttackAction) = features.forEach{ f -> f.onAttackRoll(action)}
    fun onAttackRollReceiving(action: AttackAction) = features.forEach{ f -> f.onAttackRollReceiving(action)}
    fun onDamageRoll(action: AttackAction) = features.forEach{ f -> f.onDamageRoll(action)}
    fun onDamageRollReceived(action: AttackAction) = features.forEach{ f -> f.onDamageRollReceived(action)}
    fun onDamageInflicted(action: AttackAction) = features.forEach{ f -> f.onDamageInflicted(action)}
    fun onDamageReceived(action: AttackAction) = features.forEach{ f -> f.onDamageReceived(action)}
    fun onSavingThrow(action: AttackAction) = features.forEach{ f -> f.onSavingThrow(action)}
    fun onAbilityCheck() = features.forEach{ f -> f.onAbilityCheck(this)}
    fun onShortRest() = features.forEach{ f -> f.onShortRest(this)}
    fun onLongRest() = features.forEach{ f -> f.onLongRest(this)}
    fun onKill(killed: Character) = features.forEach{ f -> f.onKill(killed, this)}

}