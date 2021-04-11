package uk.laxd.dndSimulator.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.character.CharacterFactory;
import uk.laxd.dndSimulator.event.EncounterEventFactory;
import uk.laxd.dndSimulator.event.EventLogger;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class EncounterFactory {

    private final TurnFactory turnFactory;
    private final EncounterEventFactory eventFactory;
    private final EventLogger eventLogger;
    private final CharacterFactory characterFactory;

    @Autowired
    public EncounterFactory(TurnFactory turnFactory, EncounterEventFactory eventFactory, EventLogger eventLogger, CharacterFactory characterFactory) {
        this.turnFactory = turnFactory;
        this.eventFactory = eventFactory;
        this.eventLogger = eventLogger;
        this.characterFactory = characterFactory;
    }

    public Encounter createEncounter(EncounterConfig encounterConfig) {
        Collection<Character> characters = encounterConfig.getCharacterConfigs()
                .stream()
                .map(characterFactory::createCharacter)
                .collect(Collectors.toList());

        return new Encounter(turnFactory, eventFactory, eventLogger, characters);
    }

}
