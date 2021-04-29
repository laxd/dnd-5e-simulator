package uk.laxd.dndSimulator.action

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.event.EncounterEventFactory
import uk.laxd.dndSimulator.event.EventLogger
import uk.laxd.dndSimulator.feature.Feature
import uk.laxd.dndSimulator.feature.FeatureEventProcessor
import uk.laxd.dndSimulator.feature.FeatureRegistry

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
class ActionResolver(
    private val eventLogger: EventLogger,
    private val eventFactory: EncounterEventFactory,
    private val featureEventProcessor: FeatureEventProcessor
) {
    fun resolve(action: Action) {
        // TODO: Subclass actionResolver to avoid having to do these checks?
        when(action) {
            is AttackAction -> resolveAttackAction(action)
            else -> resolveGeneralAction(action)
        }

        // TODO: Reactions
    }

    private fun resolveGeneralAction(action: Action) {
        action.performAction()
        eventLogger.logEvent(eventFactory.createNewActionEvent(action))
    }

    private fun resolveAttackAction(action: AttackAction) {
        featureEventProcessor.onAttackRoll(action)

        action.performAction()

        featureEventProcessor.onDamageRoll(action)

        action.target.applyDamage(action.attackDamage)

        if(action.attackDamage.totalAmount > 0) {
            featureEventProcessor.onDamageDealt(action)
        }

        eventLogger.logEvent(eventFactory.createNewActionEvent(action))
    }
}