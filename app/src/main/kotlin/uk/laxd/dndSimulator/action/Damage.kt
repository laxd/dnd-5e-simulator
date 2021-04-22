package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.action.DamageType
import java.util.*
import java.util.function.ToIntFunction
import java.util.stream.Collectors

/**
 * Defines the damage caused by an attack, which
 * may consist of many different types
 */
class Damage {

    val damageMap: MutableMap<DamageType, Int> = EnumMap(DamageType::class.java)

    fun getAmount(damageType: DamageType): Int {
        return damageMap.getOrDefault(damageType, 0)
    }

    fun addAmount(damageType: DamageType, amount: Int) {
        if (amount == 0) {
            return
        }
        val currentAmount = getAmount(damageType)
        damageMap[damageType] = currentAmount + amount
    }

    val totalAmount: Int
        get() = damageMap.values.stream()
            .mapToInt { v -> v }.sum()

    override fun toString(): String {
        if (damageMap.isEmpty()) {
            return "0"
        }

        return damageMap.entries.stream()
            .map { e -> "${e.value} ${e.key}"}
            .collect(Collectors.joining(", "))
    }
}