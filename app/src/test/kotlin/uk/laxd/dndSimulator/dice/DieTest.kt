package uk.laxd.dndSimulator.dice

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

internal class DieTest {

    @RepeatedTest(100)
    internal fun `Result rolled is between 1 and max value`() {
        val die = Die(6)

        val result = die.roll()

        assertTrue(result in 1..6)
    }

    @RepeatedTest(100)
    internal fun `RollResult contains multiple dice`() {
        val rolLResult = RollResult()
        rolLResult.addDice(Die.D4)
        rolLResult.addDice(Die.D4)
        rolLResult.addDice(Die.D4)

        assertTrue(rolLResult.outcome in 3..12)
    }

    @RepeatedTest(100)
    internal fun `RollResult adds modifier to outcome`() {
        val rolLResult = RollResult()
        rolLResult.addDice(Die.D4)
        rolLResult.addModifier(4)

        assertTrue(rolLResult.outcome in 5..9)
    }

    @Test
    internal fun `RollResult toString returns a given string`() {
        val rolLResult = RollResult()
        rolLResult.addDice(Die.fixedRoll(5, 20))
        rolLResult.addDice(Die.fixedRoll(10, 20))
        rolLResult.addModifier(5)

        assertEquals("2d20 (5, 10) + 5", rolLResult.toString())
    }

    @Test
    internal fun `RollResult toString returns a given string for mixed dice`() {
        val rolLResult = RollResult()
        rolLResult.addDice(Die.fixedRoll(5, 20))
        rolLResult.addDice(Die.fixedRoll(12, 20))
        rolLResult.addDice(Die.fixedRoll(4, 4))
        rolLResult.addModifier(5)

        assertEquals("2d20 (5, 12), 1d4 (4) + 5", rolLResult.toString())
    }

    @Test
    internal fun `RollResult toString returns just modifier if no dice present`() {
        val rolLResult = RollResult()
        rolLResult.addModifier(5)

        assertEquals("5", rolLResult.toString())
    }

    @Test
    internal fun `RollResult toString returns 0 if no dice or modifier present`() {
        val rolLResult = RollResult()

        assertEquals("0", rolLResult.toString())
    }
}