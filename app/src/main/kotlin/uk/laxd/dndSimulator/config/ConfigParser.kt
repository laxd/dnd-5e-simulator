package uk.laxd.dndSimulator.config

import uk.laxd.dndSimulator.config.internal.SimulationConfig

interface ConfigParser {

    fun getConfig(location: String): SimulationConfig

}