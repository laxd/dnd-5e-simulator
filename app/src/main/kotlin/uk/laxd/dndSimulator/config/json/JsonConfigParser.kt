package uk.laxd.dndSimulator.config.json

import com.beust.klaxon.Klaxon
import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.config.internal.SimulationConfig
import uk.laxd.dndSimulator.config.ConfigParser
import java.io.File
import java.lang.IllegalStateException

@Component
class JsonConfigParser(
    private val jsonConfigBuilder: JsonConfigBuilder
) : ConfigParser {
    override fun getConfig(location: String): SimulationConfig {
        val simulationConfig = Klaxon()
            .parse<JsonSimulationConfig>(File(location))
            ?: throw IllegalStateException("Could not load encounter from $location")

        return jsonConfigBuilder.createConfig(simulationConfig)
    }
}