package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.action.DamageType

class Dagger : MeleeWeapon("Dagger") {
    override var toHitModifier: Int = 0

    override fun getDamageDice(attackAction: MeleeAttackAction) = mutableListOf(Die.D4)
    override fun getDamage(attackAction: MeleeAttackAction) = 1
    override val properties = listOf(WeaponProperty.FINESSE)
    override val damageType = DamageType.PIERCING
    override val priority = 0.8
}