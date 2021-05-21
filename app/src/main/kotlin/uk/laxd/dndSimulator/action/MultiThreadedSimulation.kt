package uk.laxd.dndSimulator.action

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.config.SimulationConfig

@Component
class MultiThreadedSimulation(private val encounterFactory: EncounterFactory): Simulation {

    private var currentSimulation = 0

    private val LOGGER = LoggerFactory.getLogger(this.javaClass)

    override fun runSimulation(config: SimulationConfig) {
        LOGGER.info("Starting simulation: $config");

        runBlocking {
            (1..config.simulationCount).map {
                async {
                    val encounter = encounterFactory.createEncounter(config)
                    encounter.startEncounter()
                    LOGGER.info("Completed simulation {}/{}", ++currentSimulation, config.simulationCount)
                }
            }.awaitAll()
        }

        LOGGER.info("Finished simulations")
    }
}