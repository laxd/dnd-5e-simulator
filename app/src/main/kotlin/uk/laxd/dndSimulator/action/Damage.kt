package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.dice.RollResult
import kotlin.math.ceil

/**
 * A [Damage] represents some damage caused to a [Character]
 * by a single [Effect], inflicted by a single other [Character].
 *
 * Multiple [Damage]s may be grouped into an [AttackDamage] to represent
 * a single attack causing [Damage] from multiple sources (i.e. a sword dealing
 * slashing damage and fire damage would be one [AttackDamage] containing
 * 2 [Damage] objects.
 *
 * A [Damage] CANNOT consist of more than one damage type
 */
open class Damage(
    val damageType: DamageType,
    protected val rollResult: RollResult
) {

    constructor(damage: Damage) : this(damage.damageType, damage.rollResult)

    open fun getTotalDamage() = rollResult.outcome

    override fun toString() = "$rollResult = ${getTotalDamage()} $damageType"
}

class VulnerableDamage(damage: Damage): Damage(damage) {
    override fun getTotalDamage() = 2 * super.getTotalDamage()

    override fun toString() = "$rollResult = ${getTotalDamage()} (2x, vulnerable) $damageType "
}

class ResistantDamage(damage: Damage): Damage(damage) {
    override fun getTotalDamage() = ceil(0.5 * super.getTotalDamage()).toInt()

    override fun toString() = "$rollResult = ${getTotalDamage()} (1/2x, resistant) $damageType "
}