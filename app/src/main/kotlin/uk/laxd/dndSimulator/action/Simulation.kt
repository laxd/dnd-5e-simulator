package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.config.internal.SimulationConfig

/**
 * A simulation consists of a encounter repeated a number of times, with
 * outcomes returned for statistical analysis
 */
interface Simulation {

    fun runSimulation(config: SimulationConfig)

}