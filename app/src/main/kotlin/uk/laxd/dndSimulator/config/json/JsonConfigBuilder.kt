package uk.laxd.dndSimulator.config.json

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.config.*
import uk.laxd.dndSimulator.equipment.CustomWeapon
import uk.laxd.dndSimulator.equipment.WeaponProperty

@Component
class JsonConfigBuilder(
    private val postSimulationEventFactory: PostSimulationEventFactory
): ConfigBuilder<JsonSimulationConfig> {

    override fun createConfig(t: JsonSimulationConfig): SimulationConfig {
        val characterConfigs = mutableListOf<CharacterConfig>()
        val postSimulationEvents = mutableListOf<PostSimulationEvent>()

        for(characterConfig in t.characters) {
            val config = CharacterConfig(characterConfig.name, characterConfig.team)

            config.hp = characterConfig.hp ?: 0
            config.armourClass = characterConfig.AC ?: 0

            config.characterClassLevels.putAll(
                characterConfig.levels.mapKeys { (k, _) -> CharacterClass.valueOf(k) }
            )

            config.abilityScores.putAll(
                characterConfig.abilities.mapKeys { (k, _) -> Ability.valueOf(k) }
            )

            for(jsonWeapon in characterConfig.weapons) {
                val weapon = CustomWeapon(
                    jsonWeapon.name,
                    DamageType.valueOf(jsonWeapon.damageType.toUpperCase()),
                    jsonWeapon.diceDamage,
                    jsonWeapon.diceCount,
                    jsonWeapon.damageBonus,
                    jsonWeapon.attackBonus,
                    jsonWeapon.properties.map { p -> WeaponProperty.valueOf(p.toUpperCase().replace("-", "_")) },
                    jsonWeapon.range,
                    jsonWeapon.priority
                )

                config.weapons.add(weapon)
            }

            characterConfigs.add(config)
        }

        for(definition in t.postSimulationEventDefinitions) {
            val event = postSimulationEventFactory.createEvent(definition)

            if(event != null) {
                postSimulationEvents.add(event)
            }
        }

        return SimulationConfig(t.simulationCount, characterConfigs, postSimulationEvents)
    }
}