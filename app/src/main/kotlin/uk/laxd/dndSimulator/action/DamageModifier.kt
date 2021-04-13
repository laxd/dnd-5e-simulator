package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.dice.Die

class DamageModifier(private val damageBonus: Int, private val diceBonus: Collection<Die>) {

    val totalDamageBonus: Int
        get() = damageBonus + diceBonus.stream().mapToInt { obj: Die -> obj.roll() }.sum()

}