package uk.laxd.dndSimulator.condition;

import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.dice.Die;

public class Dead extends Unconscious {
    private int goodRolls;
    private int badRolls;

    @Override
    public void onTurnStart(Character character) {
        int rollResult = Die.D20.roll();

        if(rollResult == 1) {
            badRolls += 2;
        }
        else if(rollResult > 1 && rollResult < 10) {
            badRolls += 1;
        }
        else if(rollResult >= 10 && rollResult < 20) {
            goodRolls += 2;
        }
        else if(rollResult == 20) {
            character.setHp(1);
            // TODO: Remove this from character effects
        }

        if(badRolls >= 3) {
            // Death!
        }
        else if(goodRolls >= 3) {
            // Stabilise
        }
    }
}
