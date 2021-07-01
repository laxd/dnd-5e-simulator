package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.action.DamageType

class Dagger : Weapon("Greatsword") {
    override var toHitModifier: Int = 1

    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> {
        return mutableListOf(
                Die.D4
        )
    }

    override fun getDamage(attackAction: MeleeAttackAction): Int {
        return 1
    }

    override val properties: Collection<WeaponProperty>
        get() = listOf(
                WeaponProperty.FINESSE
        )

    override val damageType = DamageType.PIERCING
    override val range = 5
    override val priority = 0.8
}