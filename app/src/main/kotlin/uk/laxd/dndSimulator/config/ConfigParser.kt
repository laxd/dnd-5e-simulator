package uk.laxd.dndSimulator.config

interface ConfigParser {

    fun getConfig(location: String): SimulationConfig

}