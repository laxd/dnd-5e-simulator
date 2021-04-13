package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.event.EncounterEventType

/**
 * An action represents any action that a player (or creature) may take, and
 * includes, but is not limited to:
 *
 *  1. An action
 *  2. A bonus action
 *  3. Movement
 *  4. A free action
 *  5. Reactions
 *
 */
interface Action {
    fun performAction()
    val eventType: EncounterEventType?
}