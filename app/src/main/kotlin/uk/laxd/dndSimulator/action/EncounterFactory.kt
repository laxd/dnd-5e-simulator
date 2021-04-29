package uk.laxd.dndSimulator.action

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.event.EncounterEventFactory
import uk.laxd.dndSimulator.character.CharacterFactory
import uk.laxd.dndSimulator.config.CharacterConfig
import uk.laxd.dndSimulator.config.SimulationConfig
import uk.laxd.dndSimulator.event.EventLogger
import uk.laxd.dndSimulator.feature.FeatureEventProcessor
import java.util.stream.Collectors

@Component
class EncounterFactory(
    private val turnFactory: TurnFactory,
    private val eventLogger: EventLogger,
    private val characterFactory: CharacterFactory,
    private val featureEventProcessor: FeatureEventProcessor
) {
    fun createEncounter(simulationConfig: SimulationConfig): Encounter {
        val characters = characterFactory.createCharacters(simulationConfig.characterConfigs)

        // TODO: Move target selector to character factory, as it be defined externally
        return Encounter(turnFactory, eventLogger, characters, SimpleTargetSelector(characters), featureEventProcessor)
    }
}