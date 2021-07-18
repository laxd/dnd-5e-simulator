package uk.laxd.dndSimulator.config

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.equipment.Item

/**
 * A factory for creating [Item]s from given definitions.
 */
@Component
class ItemFactory {

    fun createItem(vararg equipmentDefinitions: EquipmentDefinition): List<Item> {
        return listOf()
    }

}