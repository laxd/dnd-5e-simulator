package uk.laxd.dndSimulator.dice;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Represents a modification that needs to be made to a die roll, for example:
 *
 * * Advantage - Roll twice, take highest
 * * Disadvantage - Roll twice, take lowest
 * * Bless - Add a d4
 *
 */
public abstract class RollModifier {
    public void modifyDice(Roll roll) {}

    public void modifyRoll(Roll roll) {}
}
