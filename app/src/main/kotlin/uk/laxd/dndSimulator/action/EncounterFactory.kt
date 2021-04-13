package uk.laxd.dndSimulator.action

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.event.EncounterEventFactory
import uk.laxd.dndSimulator.character.CharacterFactory
import uk.laxd.dndSimulator.character.CharacterConfig
import uk.laxd.dndSimulator.event.EventLogger
import java.util.stream.Collectors

@Component
class EncounterFactory(
    private val turnFactory: TurnFactory,
    private val eventFactory: EncounterEventFactory,
    private val eventLogger: EventLogger,
    private val characterFactory: CharacterFactory
) {
    fun createEncounter(encounterConfig: EncounterConfig): Encounter {
        val characters = encounterConfig.characterConfigs
            .stream()
            .map { characterConfig: CharacterConfig -> characterFactory.createCharacter(characterConfig) }
            .collect(Collectors.toList())

        return Encounter(turnFactory, eventLogger, characters)
    }
}