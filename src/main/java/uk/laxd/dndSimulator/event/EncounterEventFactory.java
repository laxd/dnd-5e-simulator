package uk.laxd.dndSimulator.event;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uk.laxd.dndSimulator.action.AttackAction;
import uk.laxd.dndSimulator.action.Damage;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.equipment.Weapon;

@Component
@Scope("Singleton")
public class EncounterEventFactory {

    private int count;

    public EncounterEvent createNewDamageEvent(AttackAction attackAction) {
        EncounterEvent event = new EncounterEvent(count);
        event.actor = attackAction.getPerformer();
        event.target = attackAction.getTarget();
        event.amount = attackAction.getAttackDamage();
        event.weapon = attackAction.getWeapon();
        event.type = EncounterEventType.MELEE_ATTACK;
        event.eventOutcome = attackAction.getOutcome();

        count++;

        return event;
    }

}
