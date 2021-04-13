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
open class Roll(val dice: MutableCollection<Die>) {
    private val modifier: Collection<RollModifier>? = null
    private var result: RollResult? = null

    constructor(vararg dice: Die) : this(mutableListOf(*dice))

    /**
     * Can be rolled once, and only once, to set the result of this roll.
     *
     * If this method is called multiple times, a [MultipleRollException] is thrown.
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