package uk.laxd.dndSimulator.config.json

import uk.laxd.dndSimulator.config.PostSimulationEventDefinition

class JsonSimulationConfig(
    val simulations: Int
) {

    var characters: MutableCollection<JsonCharacterConfig> = mutableListOf()
    var postSimulationEventDefinitions: MutableCollection<PostSimulationEventDefinition> = mutableListOf()

}
