package uk.laxd.dndSimulator.config.json

import com.beust.klaxon.Json
import uk.laxd.dndSimulator.config.PostSimulationEventDefinition

class JsonSimulationConfig(
    @Json(name = "simulations")
    var simulationCount: Int
) {

    var characters: MutableCollection<JsonCharacterConfig> = mutableListOf()

    @Json(name = "post")
    var postSimulationEventDefinitions: MutableCollection<PostSimulationEventDefinition> = mutableListOf()

}
