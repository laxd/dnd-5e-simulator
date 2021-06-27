package uk.laxd.dndSimulator.dice

import org.slf4j.LoggerFactory
import java.util.*

/**
 * Represents a single die, which can be rolled to get a random number
 * within its range
 */
open class Die(val maxValue: Int) {

    open fun roll(): Int {
        val result = Random().nextInt(maxValue) + 1
        LOGGER.debug("Rolled a {} = {}", this, result)
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
        private val LOGGER = LoggerFactory.getLogger(Die::class.java)
        val D4 = Die(4)
        @JvmField
        val D6 = Die(6)
        @JvmField
        val D8 = Die(8)
        @JvmField
        val D12 = Die(12)
        @JvmField
        val D20 = Die(20)

        fun fixedRoll(value: Int, maxValue: Int): Die {
            return FixedDie(value, maxValue)
        }
    }
}