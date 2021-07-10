package uk.laxd.dndSimulator.config.internal

import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.equipment.WeaponProperty

/**
 * An internal representation of a config that describes a weapon
 *
 * This allows multiple different parsers to exist, while maintaining
 * only a single representation to work with internally.
 *
 * A weapon config can either describe a weapon in terms of it's stats
 * [CustomWeaponConfig] or in terms of a reference to an existing weapon
 * [LookupWeaponConfig].
 */
abstract class WeaponConfig

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
): WeaponConfig()

class LookupWeaponConfig(
    val name: String
): WeaponConfig()