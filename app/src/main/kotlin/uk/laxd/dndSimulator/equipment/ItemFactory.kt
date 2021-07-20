package uk.laxd.dndSimulator.equipment

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.config.internal.CustomArmourConfig
import uk.laxd.dndSimulator.config.internal.CustomWeaponConfig
import uk.laxd.dndSimulator.config.internal.ItemConfig
import uk.laxd.dndSimulator.config.internal.LookupItem

/**
 * A factory for creating [Item]s from given definitions.
 */
@Component
class ItemFactory(
    private val itemRegistry: ItemRegistry
) {

    fun createItem(itemConfig: ItemConfig): Item? {
        return when(itemConfig) {
            is LookupItem -> itemRegistry.getItem(itemConfig.name, itemConfig.type)
            is CustomArmourConfig -> CustomArmour(
                itemConfig.name,
                itemConfig.armourClass,
                itemConfig.hasDexBonus,
                itemConfig.maxDexBonus,
                itemConfig.requiredStrength,
                itemConfig.disadvantageOnStealth,
                itemConfig.armourCategory
            )
            is CustomWeaponConfig -> CustomWeapon(
                itemConfig.name,
                itemConfig.damageType,
                itemConfig.diceDamage,
                itemConfig.diceCount,
                itemConfig.damageBonus,
                itemConfig.attackBonus,
                itemConfig.properties,
                itemConfig.range,
                itemConfig.priority,
                //itemConfig.proficienciesRequired
            )
            else -> throw IllegalArgumentException("Unknown type of ItemConfig: $itemConfig")
        }
    }


}