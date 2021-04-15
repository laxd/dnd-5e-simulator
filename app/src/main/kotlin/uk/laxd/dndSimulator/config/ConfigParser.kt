package uk.laxd.dndSimulator.config

import uk.laxd.dndSimulator.action.EncounterConfig

interface ConfigParser {

    fun getConfig(location: String): EncounterConfig

}