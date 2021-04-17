package uk.laxd.dndSimulator.action

import org.slf4j.LoggerFactory
import uk.laxd.dndSimulator.config.SimulationConfig

/**
 * A simulation consists of a encounter repeated a number of times, with
 * outcomes returned for statistical analysis
 */
class Simulation(private val encounterFactory: EncounterFactory) {
    val LOGGER = LoggerFactory.getLogger(this.javaClass.name)

    fun runSimulation(config: SimulationConfig, count: Int) {
        for (i in 1..count) {
            val encounter = encounterFactory.createEncounter(config)
            encounter.startEncounter()
            LOGGER.info("Completed simulation {}/{}", i, count)
        }
    }

}