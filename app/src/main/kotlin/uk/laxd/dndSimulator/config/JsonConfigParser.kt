package uk.laxd.dndSimulator.config

import com.beust.klaxon.Klaxon
import uk.laxd.dndSimulator.action.EncounterConfig
import uk.laxd.dndSimulator.config.json.JsonEncounterConfig
import java.io.File
import java.lang.IllegalStateException

class JsonConfigParser : ConfigParser {
    override fun getConfig(location: String): EncounterConfig {
        val jsonEncounterConfig = Klaxon()
            .parse<JsonEncounterConfig>(File(location))
            ?: throw IllegalStateException("Could not load encounter from $location")

        // Convert to EncounterConfig
        return jsonEncounterConfig.toConfig()
    }
}