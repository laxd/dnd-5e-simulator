package uk.laxd.dndSimulator.event

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.action.Action
import uk.laxd.dndSimulator.action.Turn
import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.character.Character

@Component
class EncounterEventFactory {

    fun createTurnStartEvent(turn: Turn): EncounterEvent {
        return TurnStartEvent(turn.character)
    }

    fun createCharacterDeathEvent(character: Character, killedBy: Character): EncounterEvent {
        return DeathEvent(character, killedBy)
    }

    fun createNewActionEvent(action: Action): EncounterEvent {
        // TODO: Handle movement, reactions, spells etc

        return when (action.eventType) {
            EncounterEventType.MELEE_ATTACK -> {
                val meleeAttackAction = action as MeleeAttackAction
                MeleeAttackEvent(
                    meleeAttackAction.actor,
                    meleeAttackAction.weapon,
                    meleeAttackAction.target,
                    meleeAttackAction.attackRollResult,
                    meleeAttackAction.damageRollResult,
                    meleeAttackAction.outcome,
                    meleeAttackAction.attackDamage
                )
            }
            else -> {
                GeneralEncounterEvent(action.actor, action.eventType)
            }
        }
    }
}