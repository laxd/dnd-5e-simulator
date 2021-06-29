package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.dice.Die

class CustomWeapon(
    name: String,
    override val damageType: DamageType,
    val diceDamage: Int,
    val diceCount: Int,
    val damageBonus: Int,
    val attackBonus: Int,
    override val properties: Collection<WeaponProperty>,
    override val range: Int,
    override val priority: Double
    ) : Weapon(name) {

    override fun getToHitModifier() = attackBonus

    override fun getDamageDice(attackAction: MeleeAttackAction): MutableCollection<Die> {
        val dice = mutableListOf<Die>()

        repeat(diceCount) {
            dice.add(Die(diceDamage))
        }

        return dice
    }

    override fun getDamage(attackAction: MeleeAttackAction): Int {
        return damageBonus
    }

    override fun toString(): String {
        return name
    }

}