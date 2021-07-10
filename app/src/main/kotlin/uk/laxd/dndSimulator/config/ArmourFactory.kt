package uk.laxd.dndSimulator.config

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.config.internal.ArmourConfig
import uk.laxd.dndSimulator.config.internal.CustomArmourConfig
import uk.laxd.dndSimulator.config.internal.LookupArmourConfig
import uk.laxd.dndSimulator.equipment.Armour
import uk.laxd.dndSimulator.equipment.ArmourRegistry
import uk.laxd.dndSimulator.equipment.CustomArmour
import uk.laxd.dndSimulator.equipment.PaddedArmour

@Component
class ArmourFactory(
    private val armourRegistry: ArmourRegistry
) {

    fun createArmour(armourConfig: ArmourConfig): Armour? {
        return when(armourConfig) {
            is LookupArmourConfig -> armourRegistry.getArmour(armourConfig.name)
            is CustomArmourConfig -> CustomArmour(
                armourConfig.name,
                armourConfig.armourClass,
                armourConfig.hasDexBonus,
                armourConfig.maxDexBonus,
                armourConfig.requiredStrength,
                armourConfig.disadvantageOnStealth,
                armourConfig.armourCategory
            )
            else -> throw IllegalArgumentException("Unknown type of ArmourConfig: $armourConfig")
        }
    }

}