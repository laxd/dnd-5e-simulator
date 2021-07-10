package uk.laxd.dndSimulator.action

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.config.CharacterFactory
import uk.laxd.dndSimulator.config.internal.SimulationConfig

@Component
class EncounterFactory(
    private val turnFactory: TurnFactory,
    private val characterFactory: CharacterFactory
) {
    fun createEncounter(simulationConfig: SimulationConfig): Encounter {
        val characters = characterFactory.createCharacters(simulationConfig.characterConfigs)

        // TODO: Move target selector to character factory, as it be defined externally
        return Encounter(turnFactory, characters, SimpleTargetSelector(characters))
    }
}