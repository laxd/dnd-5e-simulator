package uk.laxd.dndSimulator.event

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.util.ArrayList

@Component
@Scope("singleton")
class SimpleEventLogger : EventLogger {
    private var index = 1

    override val events: MutableCollection<EncounterEvent> = ArrayList()

    override fun logEvent(eventType: EncounterEventType) {
        val event = EncounterEvent()
        event.type = eventType
        logEvent(event)
    }

    override fun logEvent(event: EncounterEvent) {
        // Set the index.
        event.index = index
        events.add(event)
        index++
    }
}