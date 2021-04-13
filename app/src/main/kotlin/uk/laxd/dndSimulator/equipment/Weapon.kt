package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.equipment.WeaponProperty

abstract class Weapon {

    abstract fun getToHitModifier(attackAction: MeleeAttackAction): Int
    abstract fun getDamageDice(attackAction: MeleeAttackAction): MutableCollection<Die>

    /**
     * The additional damage portion of the weapon ONLY.
     *
     * i.e. for a 2d6 + 4 weapon, this should return 4
     * @param attackAction
     * @return
     */
    abstract fun getDamage(attackAction: MeleeAttackAction): Int
    abstract val damageType: DamageType

    open val properties: Collection<WeaponProperty>
        get() = emptyList()

    fun hasProperty(weaponProperty: WeaponProperty): Boolean {
        return properties.contains(weaponProperty)
    }

    fun onAttack(attackAction: MeleeAttackAction) {}
    fun onHit(attackAction: MeleeAttackAction) {}
}