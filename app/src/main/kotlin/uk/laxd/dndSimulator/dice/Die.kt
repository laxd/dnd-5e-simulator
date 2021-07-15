package uk.laxd.dndSimulator.dice

import org.slf4j.LoggerFactory
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * Represents a single die, which can be rolled to get a random number
 * within its range
 */
open class Die(val maxValue: Int) {
    private val logger = LoggerFactory.getLogger(Die::class.java)

    open fun roll(): Int {
        val result = Random().nextInt(maxValue) + 1
        logger.debug("Rolled a {} = {}", this, result)
        return result
    }

    override fun toString(): String {
        return "d$maxValue"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Die) return false

        if (maxValue != other.maxValue) return false

        return true
    }

    override fun hashCode(): Int {
        return maxValue
    }

    companion object {
        val D4 = Die(4)
        @JvmField
        val D6 = Die(6)
        @JvmField
        val D8 = Die(8)
        @JvmField
        val D10 = Die(10)
        @JvmField
        val D12 = Die(12)
        @JvmField
        val D20 = Die(20)

        @JvmField
        val D20_Advantage = AdvantageDie(D20)

        @JvmField
        val D20_Disadvantage = DisadvantageDie(D20)

        fun fixedRoll(value: Int, maxValue: Int): Die {
            return FixedDie(value, maxValue)
        }
    }
}

class AdvantageDie(val die: Die): Die(die.maxValue) {
    private val logger = LoggerFactory.getLogger(AdvantageDie::class.java)
    override fun roll(): Int {
        val die1 = die.roll()
        val die2 = die.roll()

        val result = max(die1, die2)

        logger.debug("Rolled a {} with advantage = {} and {} = {}", this, die1, die2, result)

        return result
    }
}

class DisadvantageDie(val die: Die): Die(die.maxValue) {
    private val logger = LoggerFactory.getLogger(DisadvantageDie::class.java)
    override fun roll(): Int {
        val die1 = die.roll()
        val die2 = die.roll()

        val result = min(die1, die2)

        logger.debug("Rolled a {} with disadvantage = {} and {} = {}", this, die1, die2, result)

        return result
    }
}