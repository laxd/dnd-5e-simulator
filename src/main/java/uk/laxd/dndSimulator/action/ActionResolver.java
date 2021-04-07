package uk.laxd.dndSimulator.action;

import org.springframework.stereotype.Component;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.dice.RollResult;
import uk.laxd.dndSimulator.equipment.Weapon;
import uk.laxd.dndSimulator.event.EncounterEventFactory;

@Component
public class ActionResolver {

    public void resolve(AttackAction attackAction) {
        Character performer = attackAction.getPerformer();
        Character target = attackAction.getTarget();

        Weapon weapon = attackAction.getWeapon();

        if (weapon == null) {
            // No attack
            return;
        }

        // Resolve all features
        performer.getFeatures().forEach(feature -> feature.onAttackRoll(attackAction));
        target.getFeatures().forEach(feature -> feature.onAttackRollReceiving(attackAction));

        // See if the attack hits
        // Roll the dice
        RollResult rollResult = attackAction.getAttackRoll().roll();

        if (attackAction.isWithAdvantage()) {
            RollResult rollResult2 = attackAction.getAttackRoll().roll();

            // Replace roll if advantage roll was greater than the first roll
            if(rollResult2.getOutcome() > rollResult.getOutcome()) {
                rollResult = rollResult2;
            }
        }
        else if(attackAction.isWithDisadvantage()) {
            RollResult rollResult2 = attackAction.getAttackRoll().roll();

            // Replace roll if advantage roll was greater than the first roll
            if(rollResult2.getOutcome() < rollResult.getOutcome()) {
                rollResult = rollResult2;
            }
        }

        attackAction.setAttackRollResult(rollResult);

        if(rollResult.getDieOutcome() == 1) {
            attackAction.setOutcome(AttackOutcome.MISS);
        }
        else if(rollResult.getDieOutcome() == 20) {
            attackAction.setOutcome(AttackOutcome.CRIT);
        }


        // If it wasn't a crit or a crit fail, see if it hits
        if(attackAction.getOutcome() == null) {
            if(rollResult.getOutcome() >= target.getArmorClass()) {
                attackAction.setOutcome(AttackOutcome.HIT);
            }
            else {
                attackAction.setOutcome(AttackOutcome.MISS);
            }
        }
    }

}
