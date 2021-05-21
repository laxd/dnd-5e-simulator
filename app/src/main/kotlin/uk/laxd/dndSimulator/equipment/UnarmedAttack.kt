package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.action.DamageType

class UnarmedAttack : Weapon("Unarmed attack") {
    override fun getToHitModifier(attackAction: MeleeAttackAction): Int {
        val attacker = attackAction.actor
        return attacker.getAbilityModifier(Ability.STRENGTH) + attacker.proficiencyBonus
    }

    override fun getDamageDice(attackAction: MeleeAttackAction): MutableCollection<Die> {
        return mutableListOf()
    }

    override fun getDamage(attackAction: MeleeAttackAction): Int {
        return attackAction.actor.getAbilityModifier(Ability.STRENGTH) + 1
    }

    override val damageType = DamageType.BLUDGEONING
    override val range = 5
    override val priority = 0.01
}