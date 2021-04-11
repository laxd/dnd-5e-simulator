package uk.laxd.dndSimulator.event;

import org.springframework.stereotype.Component;
import uk.laxd.dndSimulator.action.Action;
import uk.laxd.dndSimulator.action.MeleeAttackAction;
import uk.laxd.dndSimulator.action.Turn;

@Component
public class EncounterEventFactory {

    public EncounterEvent createTurnStartEvent(Turn turn) {
        EncounterEvent event = new EncounterEvent();
        event.type = EncounterEventType.TURN_START;
        event.actor = turn.getCharacter();

        return event;
    }

    public EncounterEvent createNewActionEvent(Action action) {
        // TODO: Handle movement, reactions, spells etc

        if(action.getEventType() == EncounterEventType.MELEE_ATTACK) {
            MeleeAttackAction meleeAttackAction = (MeleeAttackAction) action;

            EncounterEvent event = new EncounterEvent();
            event.actor = meleeAttackAction.getPerformer();
            event.target = meleeAttackAction.getTarget();
            event.amount = meleeAttackAction.getAttackDamage();
            event.weapon = meleeAttackAction.getWeapon();
            event.type = EncounterEventType.MELEE_ATTACK;
            event.eventOutcome = meleeAttackAction.getOutcome();

            return event;
        }
        else {
            EncounterEvent event = new EncounterEvent();
            event.type = action.getEventType();

            return event;
        }
    }
}
