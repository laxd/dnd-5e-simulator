package uk.laxd.dndSimulator.event

import uk.laxd.dndSimulator.character.Character

interface EventLogger {
    fun logEvent(event: EncounterEvent)
    fun logEvent(actor: Character, eventType: EncounterEventType)
    val events: Collection<EncounterEvent>

    fun logEvents(events: Collection<EncounterEvent>) {
        for(event in events) {
            logEvent(event)
        }
    }

    companion object {
        val instance = SimpleEventLogger()
    }
}