package uk.laxd.dndSimulator.config

open class SimulationConfig(
    open val simulationCount: Int,
    open val characterConfigs: Collection<CharacterConfig>,
    open val postEvents: Collection<PostSimulationEvent>
)