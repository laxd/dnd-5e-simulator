package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.feature.Effect

abstract class Equipment(name: String) : Effect(name) {

    //abstract val description: String

    open val requiresAttunement = false
    open val attuned = true
    
}