package uk.laxd.dndSimulator.config

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.config.json.JsonConfigParser

@Component
class ConfigParserFactory(
    private val jsonConfigParser: JsonConfigParser
) {

    fun getConfigParser(): ConfigParser {
        return jsonConfigParser
    }

}