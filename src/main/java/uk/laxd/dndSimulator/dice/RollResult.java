package uk.laxd.dndSimulator.dice;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RollResult {

    private Map<Die, Integer> dieResultMap = new HashMap<>();

    private int modifier = 0;

    // TODO: Include a way to add dice with a pre-determined roll? i.e. for skills that
    // set the outcome to full amount
    public void addDice(Die... dice) {
        Arrays.stream(dice)
                .forEach(d -> dieResultMap.put(d, d.roll()));
    }

    public void addModifier(int modifier) {
        this.modifier += modifier;
    }

    public int getDieOutcome() {
        // Return ONLY the sum of the dice
        return dieResultMap.values().stream().mapToInt(i -> i).sum();
    }

    // TODO: Add modifiers e.g. +2

    // TODO: Add string builder type functionality? Add decorator?

    public Integer getOutcome() {
        return getDieOutcome() + modifier;
    }

}
