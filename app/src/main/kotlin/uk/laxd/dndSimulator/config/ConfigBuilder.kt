package uk.laxd.dndSimulator.config

interface ConfigBuilder<T> {

    fun createConfig(t: T): SimulationConfig

}