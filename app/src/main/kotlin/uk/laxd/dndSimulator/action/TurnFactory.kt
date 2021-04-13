package uk.laxd.dndSimulator.action

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.event.EncounterEventFactory
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.event.EventLogger

@Component
class TurnFactory(
    private val eventFactory: EncounterEventFactory,
    private val eventLogger: EventLogger,
    private val actionResolver: ActionResolver
) {
    fun createTurn(character: Character, targetSelector: TargetSelector): Turn {
        return Turn(eventFactory, eventLogger, actionResolver, character, targetSelector)
    }
}