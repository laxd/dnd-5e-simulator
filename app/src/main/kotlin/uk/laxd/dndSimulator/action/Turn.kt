package uk.laxd.dndSimulator.action

import org.slf4j.LoggerFactory
import uk.laxd.dndSimulator.event.EncounterEventFactory
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.equipment.UnarmedAttack
import uk.laxd.dndSimulator.event.EventLogger

class Turn(
    private val eventFactory: EncounterEventFactory,
    private val eventLogger: EventLogger,
    private val actionResolver: ActionResolver,
    val character: Character,
    private val targetSelector: TargetSelector
) {
    fun doTurn() {
        eventLogger.logEvent(eventFactory.createTurnStartEvent(this))
        val target = targetSelector.findTarget(character)
        if (target == null) {
            LOGGER.info("Nothing to attack")
            return
        }

        // Find highest priority weapon with melee range
        val weapon = character.weapons
            .filter { w -> w.range <= 5 }
            .maxByOrNull { w -> w.priority } ?: UnarmedAttack()

        val attackAction = MeleeAttackAction(character, weapon, target)
        actionResolver.resolve(attackAction)
        LOGGER.debug(attackAction.toString())
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(Turn::class.java)
    }
}