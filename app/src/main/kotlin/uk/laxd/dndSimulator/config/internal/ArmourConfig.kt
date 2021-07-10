package uk.laxd.dndSimulator.config.internal

import uk.laxd.dndSimulator.equipment.ArmourCategory

open class ArmourConfig()

class CustomArmourConfig(
    val name: String,
    val armourClass: Int,
    val hasDexBonus: Boolean,
    val maxDexBonus: Int? = null,
    val requiredStrength: Int = 0,
    val disadvantageOnStealth: Boolean = false,
    val armourCategory: ArmourCategory
): ArmourConfig()

class LookupArmourConfig(
    val name: String
): ArmourConfig()