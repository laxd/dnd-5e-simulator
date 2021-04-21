package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.action.DamageType

class UnarmedAttack : Weapon() {
    override fun getToHitModifier(attackAction: MeleeAttackAction): Int {
        val attacker = attackAction.performer
        return attacker.getAbilityModifier(Ability.STRENGTH) + attacker.proficiencyBonus
    }

    override fun getDamageDice(attackAction: MeleeAttackAction): MutableCollection<Die> {
        return mutableListOf()
    }

    override fun getDamage(attackAction: MeleeAttackAction): Int {
        return attackAction.performer.getAbilityModifier(Ability.STRENGTH) + 1
    }

    override val name: String
        get() = "Unarmed attack"
    override val damageType: DamageType
        get() = DamageType.BLUDGEONING
    override val range: Int
        get() = 5
    override val priority: Double
        get() = 0.01
}