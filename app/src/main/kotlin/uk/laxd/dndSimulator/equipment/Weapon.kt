package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.action.AttackAction
import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.feature.Effect

abstract class Weapon(name: String) : Effect(name) {

    /**
     * Returns the + to hit modifier a weapon has, without taking into
     * account Str/Dex scores, proficiency scores or any other modifiers.
     */
    abstract val toHitModifier: Int
    abstract fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die>

    /**
     * The additional damage portion of the weapon ONLY.
     *
     * i.e. for a 2d6 + 4 weapon, this should return 4
     * @param attackAction
     * @return
     */
    abstract fun getDamage(attackAction: MeleeAttackAction): Int
    abstract val damageType: DamageType
    abstract val range: Int

    /**
     * Priorities determine the order in which weapons should be attempted to be used.
     *
     * This will be used e.g. when we don't have direct control over the weapons provided to a Character,
     * as we will be able to sort the weapons by priority
     */
    abstract val priority: Double

    open val properties: Collection<WeaponProperty> = mutableListOf()

    fun hasProperty(weaponProperty: WeaponProperty): Boolean {
        return properties.contains(weaponProperty)
    }
}