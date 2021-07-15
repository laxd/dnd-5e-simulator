package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.proficiency.ProficiencyAble

abstract class Weapon(name: String) : Equipment(name), ProficiencyAble {

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
    open fun getDamage(attackAction: MeleeAttackAction): Int = 0

    /**
     * Type of damage caused by this weapon. Weapons causing multiple types of damage are not currently supported
     */
    abstract val damageType: DamageType

    // TODO: Combine all these different ranges into a single collection?
    /**
     * Maximum range for a normal attack to be made. For Melee weapons, this is 5 foot.
     */
    abstract val range: Int

    /**
     * Maximum range an attack can be made at with disadvantage.
     *
     * For melee weapons, this property should be set to null.
     */
    open val longRange: Int? = null

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
    open val longThrowRange = 0

    open val properties: List<WeaponProperty> = mutableListOf()

    fun hasProperty(weaponProperty: WeaponProperty): Boolean {
        return properties.contains(weaponProperty)
    }

    override fun toString(): String = name
}

abstract class MeleeWeapon(name: String) : Weapon(name) {
    override val range = 5
}

// Simple weapons
class Club: MeleeWeapon("Club") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D4)
    override val damageType = DamageType.BLUDGEONING
    override val properties: List<WeaponProperty> = listOf(WeaponProperty.LIGHT)
    override fun getProficiencyNames(): List<String> = listOf("clubs", "simple-weapons")
}

class Dagger: MeleeWeapon("Dagger") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D4)
    override val damageType = DamageType.PIERCING
    override val properties: List<WeaponProperty> = listOf(WeaponProperty.FINESSE, WeaponProperty.LIGHT, WeaponProperty.THROWN)
    override val throwRange: Int = 20
    override val longThrowRange: Int = 60
    override fun getProficiencyNames(): List<String> = listOf("daggers", "simple-weapons")
}

class Greatclub: MeleeWeapon("Greatclub") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D8)
    override val damageType = DamageType.BLUDGEONING
    override val properties: List<WeaponProperty> = listOf(WeaponProperty.TWO_HANDED)
    override fun getProficiencyNames(): List<String> = listOf("greatclubs", "simple-weapons")
}

class Handaxe: MeleeWeapon("Handaxe") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D6)
    override val damageType = DamageType.SLASHING
    override val properties: List<WeaponProperty> = listOf(WeaponProperty.LIGHT, WeaponProperty.THROWN)
    override val throwRange: Int = 20
    override val longThrowRange: Int = 60
    override fun getProficiencyNames(): List<String> = listOf("handaxes", "simple-weapons")
}

class Javelin: MeleeWeapon("Javelin") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D6)
    override val damageType = DamageType.PIERCING
    override val properties: List<WeaponProperty> = listOf(WeaponProperty.THROWN)
    override val throwRange: Int = 30
    override val longThrowRange: Int = 120
    override fun getProficiencyNames(): List<String> = listOf("javelins", "simple-weapons")
}

class LightHammer: MeleeWeapon("Light Hammer") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D4)
    override val damageType = DamageType.BLUDGEONING
    override val properties: List<WeaponProperty> = listOf(WeaponProperty.LIGHT, WeaponProperty.THROWN)
    override val throwRange: Int = 20
    override val longThrowRange: Int = 60
    override fun getProficiencyNames(): List<String> = listOf("light-hammers", "simple-weapons")
}

class Mace: MeleeWeapon("Mace") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D6)
    override val damageType = DamageType.BLUDGEONING
    override fun getProficiencyNames(): List<String> = listOf("maces", "simple-weapons")
}

class Quarterstaff: MeleeWeapon("Quarterstaff") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D6)
    override val damageType = DamageType.BLUDGEONING
    override val properties: List<WeaponProperty> = listOf(WeaponProperty.VERSATILE)
    // versatile (1d8)
    override fun getProficiencyNames(): List<String> = listOf("quarterstaves", "simple-weapons")
}

class Sickle: MeleeWeapon("Sickle") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D4)
    override val damageType = DamageType.SLASHING
    override val properties: List<WeaponProperty> = listOf(WeaponProperty.LIGHT)
    override fun getProficiencyNames(): List<String> = listOf("sickles", "simple-weapons")
}

class Spear: MeleeWeapon("Spear") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D6)
    override val damageType = DamageType.PIERCING
    override val properties: List<WeaponProperty> = listOf(WeaponProperty.THROWN, WeaponProperty.VERSATILE)
    override val throwRange: Int = 20
    override val longThrowRange: Int = 60
    override fun getProficiencyNames(): List<String> = listOf("spears", "simple-weapons")
    // versatile (1d8)
}

class LightCrossbow: Weapon("LightCrossbow") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D8)
    override val damageType: DamageType = DamageType.PIERCING
    override val properties: List<WeaponProperty> = listOf(WeaponProperty.AMMUNITION, WeaponProperty.LOADING, WeaponProperty.TWO_HANDED)
    override val range: Int = 80
    override val longRange: Int = 320
    override fun getProficiencyNames(): List<String> = listOf("light-crossbows", "simple-weapons")
}

class Dart: Weapon("Dart") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D4)
    override val damageType: DamageType = DamageType.PIERCING
    override val properties: List<WeaponProperty> = listOf(WeaponProperty.FINESSE, WeaponProperty.THROWN)
    override val range: Int = 20
    override val longRange: Int = 60
    override fun getProficiencyNames(): List<String> = listOf("darts", "simple-weapons")
}

class Shortbow: Weapon("Shortbow") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D6)
    override val damageType: DamageType = DamageType.PIERCING
    override val properties: List<WeaponProperty> = listOf(WeaponProperty.AMMUNITION, WeaponProperty.TWO_HANDED)
    override val range: Int = 80
    override val longRange: Int = 320
    override fun getProficiencyNames(): List<String> = listOf("shortbows", "simple-weapons")
}

class Sling: Weapon("Sling") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D4)
    override val damageType: DamageType = DamageType.BLUDGEONING
    override val properties: List<WeaponProperty> = listOf(WeaponProperty.AMMUNITION)
    override val range: Int = 30
    override val longRange: Int = 120
    override fun getProficiencyNames(): List<String> = listOf("slings", "simple-weapons")
}

// Martial weapons

class Shortsword: MeleeWeapon("Shortsword") {
    override fun getDamageDice(attackAction: MeleeAttackAction): MutableList<Die> = mutableListOf(Die.D6)
    override fun getDamage(attackAction: MeleeAttackAction): Int = 0
    override val properties = listOf(WeaponProperty.FINESSE, WeaponProperty.LIGHT)
    override val damageType = DamageType.PIERCING
    override val priority = 0.8
    override fun getProficiencyNames(): List<String> = listOf("martial-weapons", "shortswords")
}