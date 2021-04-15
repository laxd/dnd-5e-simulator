package uk.laxd.dndSimulator.config.json

import uk.laxd.dndSimulator.action.EncounterConfig

class JsonEncounterConfig(
    val simulations: Int
) {

    var characters: MutableCollection<JsonCharacterConfig> = mutableListOf()

    fun toConfig() : EncounterConfig {
        return EncounterConfig(
            characters.map { j -> j.toConfig() }
        )
    }

}
