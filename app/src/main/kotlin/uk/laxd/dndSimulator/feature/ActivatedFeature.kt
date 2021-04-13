package uk.laxd.dndSimulator.feature

import uk.laxd.dndSimulator.action.ActionType

/**
 * A feature that needs to be actively used in order to be active, i.e. a Barbarian's Rage.
 */
abstract class ActivatedFeature(name: String) : Feature(name) {
    override val isActiveSkill: Boolean
        get() = true

    // TODO: Include some sort of priority so that features can be ordered?
    abstract val actionType: ActionType
}