package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.dice.Die

class CustomWeapon(
    override val name: String,
    override val damageType: DamageType,
    val diceDamage: Int,
    val diceCount: Int,
    val damageBonus: Int,
    val attackBonus: Int,
    override val properties: Collection<WeaponProperty>,
    override val range: Int,
    override val priority: Double
    ) : Weapon() {

    override fun getToHitModifier(attackAction: MeleeAttackAction): Int {
        return attackAction.performer.getAbilityModifier(Ability.STRENGTH) +
                attackAction.performer.proficiencyBonus +
                attackBonus
    }

    override fun getDamageDice(attackAction: MeleeAttackAction): MutableCollection<Die> {
        val dice = mutableListOf<Die>()

        (0..diceCount).map {
            dice.add(Die(diceDamage))
        }

        return dice
    }

    override fun getDamage(attackAction: MeleeAttackAction): Int {
        return damageBonus
    }
}