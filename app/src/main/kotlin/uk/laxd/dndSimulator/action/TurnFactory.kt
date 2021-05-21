package uk.laxd.dndSimulator.action

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.event.EncounterEventFactory
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.event.EventLogger

@Component
class TurnFactory(
    private val actionFactory: ActionFactory,
    private val eventFactory: EncounterEventFactory,
    private val eventLogger: EventLogger,
    private val actionResolver: ActionResolver
) {
    fun createTurn(character: Character, targetSelector: TargetSelector) = Turn(actionFactory, eventFactory, eventLogger, actionResolver, character, targetSelector)
}