package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.action.DamageType

class Greatsword : Weapon() {
    override fun getToHitModifier(attackAction: MeleeAttackAction): Int {
        return attackAction.performer.getAbilityModifier(Ability.STRENGTH) + attackAction.performer.proficiencyBonus + 1
    }

    override fun getDamageDice(attackAction: MeleeAttackAction): MutableCollection<Die> {
        return mutableListOf(
                Die.D6,
                Die.D6
        )
    }

    override fun getDamage(attackAction: MeleeAttackAction): Int {
        return attackAction.performer.getAbilityModifier(Ability.STRENGTH)
    }

    override val properties: Collection<WeaponProperty>
        get() = listOf(
                WeaponProperty.HEAVY
        )

    override val damageType: DamageType
        get() = DamageType.SLASHING
}