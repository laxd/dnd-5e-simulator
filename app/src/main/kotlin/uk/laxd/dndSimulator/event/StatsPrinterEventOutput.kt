package uk.laxd.dndSimulator.event

import uk.laxd.dndSimulator.statistics.StatsPrinter

class StatsPrinterEventOutput(
    private val statsPrinter: StatsPrinter
) : EventOutput {

    override fun processEvents() {
        statsPrinter.printStats();
    }
}