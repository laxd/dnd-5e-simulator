package uk.laxd.dndSimulator.action

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.config.SimulationConfig

@Component
class MultiThreadedSimulation(private val encounterFactory: EncounterFactory): Simulation {

    private var currentSimulation = 0

    private val LOGGER = LoggerFactory.getLogger(this.javaClass)

    override fun runSimulation(config: SimulationConfig, count: Int) {
            runBlocking {
                (0..count).map {
                    async {
                        val encounter = encounterFactory.createEncounter(config)
                        encounter.startEncounter()
                        LOGGER.info("Completed simulation {}/{}", ++currentSimulation, count)
                    }
                }.awaitAll()
            }

        LOGGER.info("Finished simulations")
    }
}