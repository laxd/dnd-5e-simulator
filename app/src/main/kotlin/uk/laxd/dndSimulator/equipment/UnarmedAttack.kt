package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.action.DamageType

class UnarmedAttack : Weapon("Unarmed attack") {
    override var toHitModifier = 0

    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> {
        return mutableListOf()
    }

    override fun getDamage(attackAction: MeleeAttackAction): Int {
        return attackAction.actor.getAbilityModifier(Ability.STRENGTH) + 1
    }

    override val damageType = DamageType.BLUDGEONING
    override val range = 5
    override val priority = 0.01
    override fun getProficiencyNames(): List<String> = listOf()
}