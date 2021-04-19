package uk.laxd.dndSimulator.config.json

data class JsonCharacterConfig(
    var name: String = "Un-named character",
    var hp: Int? = null,
    var AC: Int? = null,
    val levels: Map<String, Int> = mapOf(),
    val abilities: Map<String, Int> = mapOf()
)