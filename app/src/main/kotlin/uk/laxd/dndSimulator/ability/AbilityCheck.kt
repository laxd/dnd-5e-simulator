package uk.laxd.dndSimulator.ability

import org.slf4j.LoggerFactory
import kotlin.jvm.JvmOverloads
import uk.laxd.dndSimulator.dice.RollModifier
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.Die

class AbilityCheck @JvmOverloads constructor(
    val type: Ability,
    val difficultyCheck: Int,
    val performer: Character,
    private val modifier: RollModifier? = null
) {
    fun perform(): AbilityCheckOutcome {
        LOGGER.debug("Performing ability check: {} - DC: {}", type, difficultyCheck)
        val modifier = performer.getAbilityModifier(type)
        val d20Roll = Die.D20.roll()
        return if (d20Roll + modifier >= difficultyCheck) {
            LOGGER.info("$type check passed: $d20Roll + $modifier >= DC of $difficultyCheck")
            AbilityCheckOutcome.PASS
        } else {
            LOGGER.info("$type check failed: $d20Roll + $modifier < DC of $difficultyCheck")
            AbilityCheckOutcome.FAIL
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(AbilityCheck::class.java)
    }
}