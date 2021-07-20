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
import uk.laxd.dndSimulator.equipment.WeaponProperty
import uk.laxd.dndSimulator.proficiency.Proficiency
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
    val inventory: MutableCollection<JsonItem> = mutableListOf(),
    val proficiencies: MutableCollection<Proficiency> = mutableListOf()
): ConfigBuilder<CharacterConfig> {
    override fun toConfig(): CharacterConfig {
        val config = CharacterConfig(name, team, AC)

        config.hp = hp ?: 0

        config.characterClassLevels.putAll(
            levels.mapKeys { (k, _) -> CharacterClass.valueOf(k) }
        )

        config.abilityScores.putAll(
            abilities.mapKeys { (k, _) -> Ability.valueOf(k) }
        )

        inventory.forEach { i -> config.inventory.add(i.toConfig()) }

        config.proficiencies.addAll(proficiencies)

        return config
    }
}

enum class JsonItemType {
    LOOKUP_ARMOUR,
    CUSTOM_ARMOUR,
    LOOKUP_WEAPON,
    CUSTOM_WEAPON,
    LOOKUP_WONDROUS_ITEM,
    LOOKUP_CONSUMABLE
}

class ItemAdapter: TypeAdapter<JsonItem> {
    override fun classFor(type: Any): KClass<out JsonItem> {
        return when(JsonItemType.valueOf(type as String)) {
            JsonItemType.LOOKUP_ARMOUR -> LookupJsonArmour::class
            JsonItemType.CUSTOM_ARMOUR -> CustomJsonArmour::class
            JsonItemType.LOOKUP_WEAPON -> LookupJsonWeapon::class
            JsonItemType.CUSTOM_WEAPON -> CustomJsonWeapon::class
            JsonItemType.LOOKUP_WONDROUS_ITEM -> LookupJsonWondrousItem::class
            JsonItemType.LOOKUP_CONSUMABLE -> LookupJsonConsumable::class
        }
    }
}

@TypeFor(field = "type", adapter = ItemAdapter::class)
abstract class JsonItem(
    val type: JsonItemType
): ConfigBuilder<ItemConfig>

data class LookupJsonArmour(
    val name: String
): JsonItem(JsonItemType.LOOKUP_ARMOUR) {
    override fun toConfig(): LookupItem {
        return LookupItem(name, ItemType.ARMOUR)
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
): JsonItem(JsonItemType.CUSTOM_ARMOUR) {
    override fun toConfig(): CustomArmourConfig {
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

data class LookupJsonWeapon(
    val name: String
): JsonItem(JsonItemType.LOOKUP_WEAPON) {
    override fun toConfig(): LookupItem {
        return LookupItem(name, ItemType.WEAPON)
    }
}

data class CustomJsonWeapon(
    val name: String,
    val damageType: String,
    val diceDamage: Int,
    val diceCount: Int,
    val damageBonus: Int,
    val attackBonus: Int,
    val properties: Collection<String>,
    val range: Int,
    val priority: Double = 1.0
) : JsonItem(JsonItemType.CUSTOM_WEAPON) {
    override fun toConfig(): CustomWeaponConfig {
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

data class LookupJsonWondrousItem(
    val name: String
): JsonItem(JsonItemType.LOOKUP_WONDROUS_ITEM) {
    override fun toConfig(): LookupItem {
        return LookupItem(name, ItemType.WONDROUS_ITEM)
    }
}

data class LookupJsonConsumable(
    val name: String
): JsonItem(JsonItemType.LOOKUP_CONSUMABLE) {
    override fun toConfig(): LookupItem {
        return LookupItem(name, ItemType.CONSUMABLE)
    }
}