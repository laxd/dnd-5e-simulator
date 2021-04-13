package uk.laxd.dndSimulator.dice

import java.util.HashMap
import java.util.Arrays
import java.util.function.Consumer

class RollResult {
    private val dieResultMap: MutableMap<Die, Int> = HashMap()
    private var modifier = 0

    // TODO: Include a way to add dice with a pre-determined roll? i.e. for skills that
    // set the outcome to full amount
    fun addDice(vararg dice: Die) {
        Arrays.stream(dice)
                .forEach { d: Die -> dieResultMap[d] = d.roll() }
    }

    fun addModifier(modifier: Int) {
        this.modifier += modifier
    }

    // Return ONLY the sum of the dice
    val dieOutcome: Int
        get() =// Return ONLY the sum of the dice
            dieResultMap.values.stream().mapToInt { i: Int? -> i!! }.sum()

    // TODO: Add modifiers e.g. +2
    // TODO: Add string builder type functionality? Add decorator?
    val outcome: Int
        get() = dieOutcome + modifier
}