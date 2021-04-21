package uk.laxd.dndSimulator.config.json

data class JsonWeapon(
    val name: String,
    val damageType: String,
    val diceDamage: Int,
    val diceCount: Int,
    val damageBonus: Int,
    val attackBonus: Int,
    val properties: Collection<String>,
    val range: Int,
    val priority: Double = 1.0
)