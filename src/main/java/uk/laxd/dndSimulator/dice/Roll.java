package uk.laxd.dndSimulator.dice;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A roll of one or more dice, with zero or one modifiers.
 *
 * I.e. a Strength check may be represented by a Roll object, e.g.:
 *
 * 1d20 + 2
 */
public class Roll {

    private Collection<Die> dice;

    private Collection<RollModifier> modifier;

    private RollResult result;

    public Roll(Collection<Die> dice) {
        this.dice = dice;
    }

    public Roll(Die... dice) {
        this.dice = Arrays.asList(dice);
    }

    /**
     * Can be rolled once, and only once, to set the result of this roll.
     *
     * If this method is called multiple times, a {@link MultipleRollException} is thrown.
     */
    public RollResult roll() {

        // Any RollModifiers that modify the sum of the dice should be included here,
        // i.e. bless adding a d4, rage adding 2 to strength checks etc.
        // Any other modifiers that modify HOW the roll is made (i.e. advantage/disadvantage)
        // will be taken into account outside of this class


        result = new RollResult();
        result.addDice(dice.toArray(new Die[]{}));

        return result;
    }

    public void addDie(Die die) {
        this.dice.add(die);
    }

    public Collection<Die> getDice() {
        return dice;
    }

    @Override
    public String toString() {
        return dice.stream()
                .collect(Collectors.groupingBy(Die::getMaxValue)).entrySet()
                .stream()
                .map(e -> e.getValue().size() + "d" + e.getKey())
                .collect(Collectors.joining(" ")) + "(" + result + ")";
    }
}
