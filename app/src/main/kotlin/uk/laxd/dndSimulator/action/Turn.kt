package uk.laxd.dndSimulator.action

import org.slf4j.LoggerFactory
import uk.laxd.dndSimulator.event.EncounterEventFactory
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.event.EventLogger
import uk.laxd.dndSimulator.event.TurnEndEvent
import uk.laxd.dndSimulator.event.TurnStartEvent

class Turn(
    private val actionFactory: ActionFactory,
    private val eventFactory: EncounterEventFactory,
    private val eventLogger: EventLogger,
    private val actionResolver: ActionResolver,
    val character: Character,
    private val targetSelector: TargetSelector
) {
    private val logger = LoggerFactory.getLogger(Turn::class.java)

    fun doTurn() {
        eventLogger.logEvent(TurnStartEvent(character))

        repeat(character.attackCount) {
            logger.debug("Attack $it/${character.attackCount}")

            val attackAction = actionFactory.createAction(character, targetSelector)
            actionResolver.resolve(attackAction)
            logger.debug(attackAction.toString())
        }

        eventLogger.logEvent(TurnEndEvent(character))
    }
}