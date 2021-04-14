package uk.laxd.dndSimulator.event

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.statistics.StatsPrinter

@Component
class EventOutputFactory(
    private val statsPrinter: StatsPrinter
) {

    fun getEventOutput(): EventOutput {
        // TODO: Switch on input from CLI/Application

        return StatsPrinterEventOutput(statsPrinter)
    }

}