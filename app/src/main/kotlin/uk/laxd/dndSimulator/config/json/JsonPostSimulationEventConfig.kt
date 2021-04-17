package uk.laxd.dndSimulator.config.json

import uk.laxd.dndSimulator.config.PostSimulationEventDefinition

class JsonPostSimulationEventConfig(type: String, options: Map<String, String>) :
    PostSimulationEventDefinition(type, options)