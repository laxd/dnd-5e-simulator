package uk.laxd.dndSimulator.config

import uk.laxd.dndSimulator.config.internal.SimulationConfig

interface ConfigBuilder<T> {

    fun createConfig(t: T): SimulationConfig

}