package uk.laxd.dndSimulator.config.json

data class JsonCharacterConfig(
    var name: String,
    var team: String,
    var hp: Int? = null,
    var AC: Int? = null,
    val levels: Map<String, Int> = mapOf(),
    val abilities: Map<String, Int> = mapOf(),
    val weapons: MutableCollection<JsonWeapon> = mutableListOf()
)