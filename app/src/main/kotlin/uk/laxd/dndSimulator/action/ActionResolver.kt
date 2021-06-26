package uk.laxd.dndSimulator.action

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.event.EncounterEventFactory
import uk.laxd.dndSimulator.event.EventLogger

/**
 * Resolves an action, which may involve any of the following:
 *
 *  1. An action
 *  2. A bonus action
 *  3. Movement
 *  4. A free action
 *
 * Resolving an action plays out the intended action, recording the result
 * and resolving all outcomes from that actions (i.e. for attacks, applies
 * damage to the creature being attacked).
 *
 * Reactions as a result of the action resolving (i.e. Opportunity attacks, etc)
 * are resolved as part of this class, and will log their own events etc.
 *
 */
@Component
class ActionResolver() {
    fun resolve(action: Action) {
        action.performAction()

        // TODO: Reactions
    }
}