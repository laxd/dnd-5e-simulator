package uk.laxd.dndSimulator.config

import org.springframework.context.support.beans
import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.statistics.PrintStatsPostSimulationEvent

@Component
class PostSimulationEventFactory(
    private val printStatsPostSimulationEvent: PrintStatsPostSimulationEvent
) {

    fun createEvent(eventDefinition: PostSimulationEventDefinition) : PostSimulationEvent? {
        return when (eventDefinition.type) {
            "printStats" -> printStatsPostSimulationEvent
            else -> null
        }
    }

}