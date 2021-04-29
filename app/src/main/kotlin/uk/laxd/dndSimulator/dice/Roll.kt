package uk.laxd.dndSimulator.dice

import java.util.Arrays
import java.util.stream.Collectors

/**
 * A roll of one or more dice, with zero or one modifiers.
 *
 * I.e. a Strength check may be represented by a Roll object, e.g.:
 *
 * 1d20 + 2
 */
open class Roll(val dice: MutableCollection<Die>, val modifier: Int = 0) {
    private var result: RollResult? = null

    constructor(vararg dice: Die, modifier: Int = 0) : this(mutableListOf(*dice), modifier)

    /**
     * Creates a random number between 1 + [modifier] and [Die.maxValue] + [modifier]
     */
    open fun roll(): RollResult {
        // Any RollModifiers that modify the sum of the dice should be included here,
        // i.e. bless adding a d4, rage adding 2 to strength checks etc.
        // Any other modifiers that modify HOW the roll is made (i.e. advantage/disadvantage)
        // will be taken into account outside of this class
        val result = RollResult()

        for(die in dice) {
            result.addDice(die)
        }

        result.addModifier(modifier)

        return result
    }

    fun addDie(die: Die) {
        this.dice.add(die)
    }

    override fun toString(): String {
        return this.dice.stream()
                .collect(Collectors.groupingBy(Die::maxValue)).entries
                .stream()
                .map { e: Map.Entry<Int, List<Die?>> -> e.value.size.toString() + "d" + e.key }
                .collect(Collectors.joining(" ")).toString() + "(" + result + ")"
    }
}

class AdvantageRoll(val roll: Roll): Roll(roll.dice, roll.modifier) {
    override fun roll(): RollResult {
        return listOf(super.roll(), super.roll())
            .maxByOrNull { r -> r.outcome }!!
    }

    override fun toString(): String {
        return "AdvantageRoll(roll=$roll)"
    }

}

class DisadvantageRoll(val roll: Roll): Roll(roll.dice, roll.modifier) {
    override fun roll(): RollResult {
        return listOf(super.roll(), super.roll())
            .minByOrNull { r -> r.outcome }!!
    }
}