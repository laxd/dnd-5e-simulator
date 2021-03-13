package uk.laxd.dndSimulator.dice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Represents a single die, which can be rolled to get a random number
 * within its range
 */
public class Die {

    private static final Logger LOGGER = LoggerFactory.getLogger(Die.class);

    public static final Die D4 = new Die(4);
    public static final Die D6 = new Die(6);
    public static final Die D8 = new Die(8);
    public static final Die D12 = new Die(12);
    public static final Die D20 = new Die(20);

    private int sides;

    public Die(int sides) {
        this.sides = sides;
    }

    public int roll() {
        int result = new Random().nextInt(sides) + 1;
        LOGGER.debug("Rolled a {} = {}", this, result);
        return result;
    }

    public int getMaxValue() {
        return sides;
    }

    @Override
    public String toString() {
        return String.format("d%s", sides);
    }
}
