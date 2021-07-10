package uk.laxd.dndSimulator.config.json

import com.beust.klaxon.Json
import com.beust.klaxon.TypeAdapter
import com.beust.klaxon.TypeFor
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.config.PostSimulationEventDefinition
import uk.laxd.dndSimulator.config.internal.*
import uk.laxd.dndSimulator.equipment.ArmourCategory
import uk.laxd.dndSimulator.equipment.CustomWeapon
import uk.laxd.dndSimulator.equipment.WeaponProperty
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
): CharacterConfigBuilder {
    override fun toCharacterConfig(): CharacterConfig {
        val config = CharacterConfig(name, team, AC)

        config.hp = hp ?: 0

        config.characterClassLevels.putAll(
            levels.mapKeys { (k, _) -> CharacterClass.valueOf(k) }
        )

        config.abilityScores.putAll(
            abilities.mapKeys { (k, _) -> Ability.valueOf(k) }
        )

        weapons.forEach { w -> config.weapons.add(w.toWeaponConfig()) }
        armour.forEach { a -> config.armour.add(a.toArmourConfig()) }

        return config
    }
}

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
abstract class JsonArmour(
    val type: ArmourType
): ArmourConfigBuilder

data class LookupJsonArmour(
    val name: String
): JsonArmour(ArmourType.LOOKUP) {
    override fun toArmourConfig(): ArmourConfig {
        return LookupArmourConfig(name)
    }
}

data class CustomJsonArmour(
    val name: String,
    val ac: Int,
    val hasDexBonus: Boolean,
    val maxDexBonus: Int? = null,
    val requiredStrength: Int = 0,
    val disadvantageOnStealth: Boolean = false,
    val category: ArmourCategory
): JsonArmour(ArmourType.CUSTOM) {
    override fun toArmourConfig(): ArmourConfig {
        return CustomArmourConfig(
            name,
            ac,
            hasDexBonus,
            maxDexBonus,
            requiredStrength,
            disadvantageOnStealth,
            category
        )
    }
}

// TODO: Refactor to CustomJsonWeapon and LookupJsonWeapon, allowing adding base weapons just by name
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
) : WeaponConfigBuilder {
    override fun toWeaponConfig(): WeaponConfig {
        return CustomWeaponConfig(
            this.name,
            DamageType.valueOf(this.damageType.toUpperCase()),
            this.diceDamage,
            this.diceCount,
            this.damageBonus,
            this.attackBonus,
            this.properties.map { p -> WeaponProperty.valueOf(p.toUpperCase().replace("-", "_")) },
            this.range,
            this.priority
        )
    }
}