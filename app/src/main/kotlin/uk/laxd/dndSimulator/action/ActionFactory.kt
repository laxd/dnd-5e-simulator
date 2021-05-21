package uk.laxd.dndSimulator.action

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.equipment.UnarmedAttack
import uk.laxd.dndSimulator.event.EncounterEventType

@Component
class ActionFactory {

    private val logger = LoggerFactory.getLogger(ActionFactory::class.java)

    /**
     * Currently only handles melee attack actions
     */
    fun createAction(character: Character, targetSelector: TargetSelector): Action {
        val target = targetSelector.findTarget(character)
        if (target == null) {
            logger.debug("Nothing to attack")
            return NoOpAction(character, EncounterEventType.HOLD_TURN)
        }

        // Find highest priority weapon with melee range
        val weapon = character.weapons
            .filter { w -> w.range <= 5 }
            .maxByOrNull { w -> w.priority } ?: UnarmedAttack()

        return MeleeAttackAction(character, weapon, target)
    }

}