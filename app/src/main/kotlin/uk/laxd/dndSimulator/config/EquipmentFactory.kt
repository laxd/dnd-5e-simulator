package uk.laxd.dndSimulator.config

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.equipment.Equipment

/**
 * A factory for creating [Equipment] from given definitions.
 */
@Component
class EquipmentFactory {

    fun createEquipment(vararg equipmentDefinitions: EquipmentDefinition): List<Equipment> {
        return listOf()
    }

}