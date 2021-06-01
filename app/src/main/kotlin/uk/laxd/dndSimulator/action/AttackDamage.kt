package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.dice.RollResult
import uk.laxd.dndSimulator.feature.Effect
import java.util.stream.Collectors

/**
 * Defines the damage caused by an attack to a single [Character]. AOE attacks, etc
 * which hit multiple targets generate a new [AttackDamage] for each target hit.
 *
 * An [AttackDamage] consists of all of the different types of damage caused by
 * an attack, including any additional damage caused by any [Effect]s triggered by the
 * attack.
 *
 * For example: A Barbarian attacking with Rage, generates an [AttackDamage] like this:
 *
 * AttackDamage{
 *   damageMap: {
 *     weapon: 2d6 slashing,
 *     rage: 2 slashing
 *   }
 * }
 */
class AttackDamage {

    val damageMap: MutableMap<Effect, Damage> = mutableMapOf()

    fun getAmount(damageType: DamageType): Int {
        return damageMap.values
            .filter { d -> d.damageType == damageType }
            .sumOf { d -> d.getTotalDamage() }
    }

    fun addDamage(source: Effect, type: DamageType, amount: Int) {
        addDamage(source, type, RollResult(modifier = amount))
    }

    fun addDamage(source: Effect, type: DamageType, rollResult: RollResult) {
        if (rollResult.outcome == 0) {
            return
        }
        damageMap[source] = Damage(type, rollResult)
    }

    val totalAmount: Int
        get() = damageMap.values.stream()
            .mapToInt { v -> v.getTotalDamage() }.sum()

    override fun toString(): String {
        if (damageMap.isEmpty()) {
            return "0"
        }

        return damageMap.entries.stream()
            .map { e -> "${e.value} damage from ${e.key}"}
            .collect(Collectors.joining(", "))
    }
}