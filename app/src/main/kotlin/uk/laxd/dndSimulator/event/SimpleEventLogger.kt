package uk.laxd.dndSimulator.event

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.character.Character

@Component
@Scope("singleton")
class SimpleEventLogger : EventLogger {
    override val events: MutableList<EncounterEvent> = mutableListOf()

    override fun logEvent(actor: Character, eventType: EncounterEventType) {
        logEvent(GeneralEncounterEvent(actor, eventType))
    }

    override fun logEvent(event: EncounterEvent) {
        events.add(event)
    }
}