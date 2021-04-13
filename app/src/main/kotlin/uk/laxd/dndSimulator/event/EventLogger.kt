package uk.laxd.dndSimulator.event

interface EventLogger {
    fun logEvent(event: EncounterEvent)
    fun logEvent(eventType: EncounterEventType)
    val events: Collection<EncounterEvent>
}