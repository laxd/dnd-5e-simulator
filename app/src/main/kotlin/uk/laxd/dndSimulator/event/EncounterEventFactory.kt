package uk.laxd.dndSimulator.event

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.action.Action
import uk.laxd.dndSimulator.action.Turn
import uk.laxd.dndSimulator.action.MeleeAttackAction

@Component
class EncounterEventFactory {

    fun createTurnStartEvent(turn: Turn): EncounterEvent {
        val event = EncounterEvent()
        event.type = EncounterEventType.TURN_START
        event.actor = turn.character
        return event
    }

    fun createNewActionEvent(action: Action): EncounterEvent {
        // TODO: Handle movement, reactions, spells etc

        return when (action.eventType) {
            EncounterEventType.MELEE_ATTACK -> {
                val meleeAttackAction = action as MeleeAttackAction
                EncounterEvent().apply {
                    actor = meleeAttackAction.performer
                    target = meleeAttackAction.target
                    amount = meleeAttackAction.attackDamage
                    weapon = meleeAttackAction.weapon
                    type = EncounterEventType.MELEE_ATTACK
                    eventOutcome = meleeAttackAction.outcome
                }
            }
            else -> {
                EncounterEvent().apply {
                    type = action.eventType
                }
            }
        }
    }
}