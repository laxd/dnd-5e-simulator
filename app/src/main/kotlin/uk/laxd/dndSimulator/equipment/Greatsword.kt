package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.action.DamageType

class Greatsword : Weapon("Greatsword") {
    override fun getToHitModifier(attackAction: MeleeAttackAction): Int {
        return attackAction.actor.getAbilityModifier(Ability.STRENGTH) + attackAction.actor.proficiencyBonus + 1
    }

    override fun getDamageDice(attackAction: MeleeAttackAction): MutableCollection<Die> {
        return mutableListOf(
                Die.D6,
                Die.D6
        )
    }

    override fun getDamage(attackAction: MeleeAttackAction): Int {
        return attackAction.actor.getAbilityModifier(Ability.STRENGTH)
    }

    override val properties: Collection<WeaponProperty>
        get() = listOf(
                WeaponProperty.HEAVY
        )

    override val damageType = DamageType.SLASHING
    override val range = 5
    override val priority = 0.8
}