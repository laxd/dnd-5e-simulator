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
    override val properties: List<WeaponProperty>,
    override val range: Int,
    override val priority: Double,
    val proficienciesRequired: List<String> = listOf()
    ) : Weapon(name) {

    override var toHitModifier = attackBonus

    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> {
        val dice = mutableListOf<Die>()

        repeat(diceCount) {
            dice.add(Die(diceDamage))
        }

        return dice
    }

    override fun getDamage(attackAction: MeleeAttackAction): Int = damageBonus
    override fun getProficiencyNames(): List<String> = proficienciesRequired
}