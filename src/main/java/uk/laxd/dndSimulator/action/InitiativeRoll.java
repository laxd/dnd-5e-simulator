package uk.laxd.dndSimulator.action;

import uk.laxd.dndSimulator.dice.Die;
import uk.laxd.dndSimulator.dice.Roll;
import uk.laxd.dndSimulator.dice.RollModifier;

import java.util.Collection;

public class InitiativeRoll extends Roll {

    public InitiativeRoll() {
        super(Die.D20);
    }

}
