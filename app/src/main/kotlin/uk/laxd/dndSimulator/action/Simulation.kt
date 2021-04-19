package uk.laxd.dndSimulator.action

import org.slf4j.LoggerFactory
import uk.laxd.dndSimulator.config.SimulationConfig

/**
 * A simulation consists of a encounter repeated a number of times, with
 * outcomes returned for statistical analysis
 */
interface Simulation {

    fun runSimulation(config: SimulationConfig)

}