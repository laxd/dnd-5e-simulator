package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.action.AttackAction
import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.action.DamageType

abstract class Weapon(name: String) : Equipment(name) {

    /**
     * Returns the + to hit modifier a weapon has, without taking into
     * account Str/Dex scores, proficiency scores or any other modifiers.
     */
    open val toHitModifier: Int = 0

    /**
     * The dice portion of the weapon ONLY.
     *
     * i.e. for a 2d6 + 4 weapon, this should return a list containing {D6, D6}
     */
    abstract fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die>

    /**
     * The additional damage portion of the weapon ONLY.
     *
     * i.e. for a 2d6 + 4 weapon, this should return 4
     * @param attackAction
     * @return
     */
    abstract fun getDamage(attackAction: MeleeAttackAction): Int

    /**
     * Type of damage caused by this weapon. Weapons causing multiple types of damage are not currently supported
     */
    abstract val damageType: DamageType

    /**
     * Maximum range for a normal attack to be made. For Melee weapons, this is 5 foot.
     */
    abstract val range: Int

    /**
     * For weapons that have the "Thrown" property, this is the range at which a thrown attack
     * may be made without imposing disadvantage on the attack.
     *
     * For weapons that do NOT have the "Thrown" property, this value is ignored.
     */
    open val throwRange: Int = 0

    /**
     * For weapons that have the "Thrown" property, this is the maximum range at which a thrown attack
     * may be made. If this is greater than [throwRange], the attack roll is made at disadvantage.
     *
     * For weapons that do NOT have the "Thrown" property, this value is ignored.
     */
    open val throwRangeDisadvantage = 0

    open val properties: List<WeaponProperty> = mutableListOf()

    fun hasProperty(weaponProperty: WeaponProperty): Boolean {
        return properties.contains(weaponProperty)
    }

    override fun toString(): String = name
}

abstract class MeleeWeapon(name: String) : Weapon(name) {
    override val range = 5
}