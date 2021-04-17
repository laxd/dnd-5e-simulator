package uk.laxd.dndSimulator.statistics

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.config.PostSimulationEvent

@Component
class PrintStatsPostSimulationEvent(
    private val statsPrinter: StatsPrinter
) : PostSimulationEvent {

    override fun doEvent() {
        statsPrinter.printStats()
    }
}