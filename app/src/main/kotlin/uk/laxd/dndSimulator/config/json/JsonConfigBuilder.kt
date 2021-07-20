package uk.laxd.dndSimulator.config.json

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.config.*
import uk.laxd.dndSimulator.config.internal.CharacterConfig
import uk.laxd.dndSimulator.config.internal.SimulationConfig
import uk.laxd.dndSimulator.equipment.*

@Component
class JsonConfigBuilder(
    private val postSimulationEventFactory: PostSimulationEventFactory
): ConfigBuilder<JsonSimulationConfig> {

    override fun createConfig(t: JsonSimulationConfig): SimulationConfig {
        val characterConfigs = mutableListOf<CharacterConfig>()
        val postSimulationEvents = mutableListOf<PostSimulationEvent>()

        for(jsonCharacterConfig in t.characters) {
            val config = jsonCharacterConfig.toConfig()

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