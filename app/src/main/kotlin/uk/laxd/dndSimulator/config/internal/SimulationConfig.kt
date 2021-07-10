package uk.laxd.dndSimulator.config.internal

import uk.laxd.dndSimulator.config.PostSimulationEvent

open class SimulationConfig(
    open val simulationCount: Int,
    open val characterConfigs: Collection<CharacterConfig>,
    open val postEvents: Collection<PostSimulationEvent>
) {
    override fun toString(): String {
        return "SimulationConfig(simulationCount=$simulationCount, characterConfigs=$characterConfigs, postEvents=$postEvents)"
    }
}