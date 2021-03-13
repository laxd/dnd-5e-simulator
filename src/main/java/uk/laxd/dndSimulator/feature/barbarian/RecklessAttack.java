package uk.laxd.dndSimulator.feature.barbarian;

import uk.laxd.dndSimulator.action.ActionType;
import uk.laxd.dndSimulator.action.AttackAction;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.dice.Roll;
import uk.laxd.dndSimulator.feature.ActivatedFeature;

public class RecklessAttack extends ActivatedFeature {

    public RecklessAttack() {
        super("Reckless Attack");
    }

    @Override
    public void onAttackRoll(AttackAction action) {
        // Attack recklessly every time
//        reckless = true;
        // Add advantage
        if(isActive()) {
            action.setWithAdvantage(true);
        }
    }

    @Override
    public void onAttackRollReceiving(AttackAction action) {
        // Add advantage
        if(isActive()) {
            action.setWithAdvantage(true);
        }
    }

    @Override
    public void onTurnStart(Character character) {
        // Reset state at the start of every turn.
        deactivate();
    }

    @Override
    public int getLevelRequirement() {
        return 2;
    }

    @Override
    public void reset() {
        super.deactivate();
    }

    @Override
    public ActionType getActionType() {
        return ActionType.FREE_ACTION;
    }
}
