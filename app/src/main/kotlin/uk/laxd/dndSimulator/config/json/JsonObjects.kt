package uk.laxd.dndSimulator.config.json

import com.beust.klaxon.Json
import com.beust.klaxon.TypeAdapter
import com.beust.klaxon.TypeFor
import uk.laxd.dndSimulator.config.PostSimulationEventDefinition
import uk.laxd.dndSimulator.equipment.ArmourCategory
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

data class JsonSimulationConfig(
    @Json(name = "simulations") var simulationCount: Int,
    @Json(name = "characters") var characters: MutableCollection<JsonCharacterConfig> = mutableListOf(),
    @Json(name = "post") var postSimulationEventDefinitions: MutableCollection<PostSimulationEventDefinition> = mutableListOf()
)

data class JsonCharacterConfig(
    var name: String,
    var team: String,
    var hp: Int? = null,
    var AC: Int? = null,
    val levels: Map<String, Int> = mapOf(),
    val abilities: Map<String, Int> = mapOf(),
    val armour: MutableCollection<JsonArmour> = mutableListOf(),
    val weapons: MutableCollection<JsonWeapon> = mutableListOf()
)

enum class ArmourType {
    LOOKUP, CUSTOM
}

class ArmourAdapter: TypeAdapter<JsonArmour> {
    override fun classFor(type: Any): KClass<out JsonArmour> {
        return when(ArmourType.valueOf(type as String)) {
            ArmourType.LOOKUP -> LookupJsonArmour::class
            ArmourType.CUSTOM -> CustomJsonArmour::class
        }
    }
}

@TypeFor(field = "type", adapter = ArmourAdapter::class)
open class JsonArmour(
    val type: ArmourType
)

data class LookupJsonArmour(
    var name: String
): JsonArmour(ArmourType.LOOKUP)

data class CustomJsonArmour(
    val name: String,
    val ac: Int,
    val requiredStrength: Int = 0,
    val disadvantageOnStealth: Boolean = false,
    val category: ArmourCategory
): JsonArmour(ArmourType.CUSTOM)

// TODO: CustomJsonWeapon? Allowing adding base weapons just by name
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