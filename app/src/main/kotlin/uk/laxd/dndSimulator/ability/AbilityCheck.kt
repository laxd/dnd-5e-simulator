package uk.laxd.dndSimulator.ability

import org.slf4j.LoggerFactory
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.dice.Roll

open class AbilityCheck constructor(
    val type: Ability,
    val difficultyCheck: Int,
    val performer: Character,
) {
    private val logger = LoggerFactory.getLogger(AbilityCheck::class.java)

    fun perform(): AbilityCheckOutcome {
        logger.debug("Performing ability check: {} - DC: {}", type, difficultyCheck)
        val rollResult = Roll(Die.D20, modifier = performer.getAbilityModifier(type)).roll()

        return if (rollResult.outcome >= difficultyCheck) {
            logger.info("$type check passed: $rollResult >= DC of $difficultyCheck")
            AbilityCheckOutcome.PASS
        } else {
            logger.info("$type check failed: $rollResult < DC of $difficultyCheck")
            AbilityCheckOutcome.FAIL
        }
    }
}