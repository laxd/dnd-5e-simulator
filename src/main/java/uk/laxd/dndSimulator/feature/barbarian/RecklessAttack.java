package uk.laxd.dndSimulator.feature.barbarian;

import uk.laxd.dndSimulator.action.ActionType;
import uk.laxd.dndSimulator.action.MeleeAttackAction;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.character.CharacterClass;
import uk.laxd.dndSimulator.feature.ActivatedFeature;

public class RecklessAttack extends ActivatedFeature {

    public RecklessAttack() {
        super("Reckless Attack");
    }

    @Override
    public void onAttackRoll(MeleeAttackAction action) {
        // Attack recklessly every time
//        reckless = true;
        // Add advantage
        if(isActive()) {
            action.setWithAdvantage(true);
        }
    }

    @Override
    public void onAttackRollReceiving(MeleeAttackAction action) {
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
    public CharacterClass getCharacterClassRequired() {
        return CharacterClass.BARBARIAN;
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
