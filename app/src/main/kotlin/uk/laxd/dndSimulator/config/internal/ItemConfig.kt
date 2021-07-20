package uk.laxd.dndSimulator.config.internal

import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.equipment.ArmourCategory
import uk.laxd.dndSimulator.equipment.WeaponProperty

open class ItemConfig

class CustomArmourConfig(
    val name: String,
    val armourClass: Int,
    val hasDexBonus: Boolean,
    val maxDexBonus: Int? = null,
    val requiredStrength: Int = 0,
    val disadvantageOnStealth: Boolean = false,
    val armourCategory: ArmourCategory
): ItemConfig()

class CustomWeaponConfig(
    val name: String,
    val damageType: DamageType,
    val diceDamage: Int,
    val diceCount: Int,
    val damageBonus: Int,
    val attackBonus: Int,
    val properties: List<WeaponProperty>,
    val range: Int,
    val priority: Double = 1.0
): ItemConfig()

enum class ItemType {
    WEAPON,
    ARMOUR,
    WONDROUS_ITEM,
    CONSUMABLE
}

class LookupItem(
    val name: String,
    val type: ItemType
): ItemConfig()