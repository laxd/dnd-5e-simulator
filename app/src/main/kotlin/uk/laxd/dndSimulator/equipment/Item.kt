package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.feature.Effect

abstract class Item(name: String) : Effect(name) {

    //abstract val description: String
    /**
     * Priorities determine the order in which equipment should be attempted to be used.
     *
     * This will be used e.g. when we don't have direct control over the equipment provided to a Character,
     * as we will be able to sort the weapons/armour by priority
     */
    open val priority: Double = 0.5

    open val requiresAttunement = false
    open val attuned = true
    
}