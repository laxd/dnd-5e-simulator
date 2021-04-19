package uk.laxd.dndSimulator.action

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.config.SimulationConfig

class SingleThreadedSimulation(private val encounterFactory: EncounterFactory): Simulation {
    val LOGGER = LoggerFactory.getLogger(this.javaClass.name)

    override fun runSimulation(config: SimulationConfig, count: Int) {
        for (i in 1..count) {
            val encounter = encounterFactory.createEncounter(config)
            encounter.startEncounter()
            LOGGER.info("Completed simulation {}/{}", i, count)
        }
    }
}