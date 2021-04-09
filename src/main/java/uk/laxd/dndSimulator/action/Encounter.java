package uk.laxd.dndSimulator.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.event.EncounterEventFactory;
import uk.laxd.dndSimulator.event.EventLogger;

import java.util.*;
import java.util.stream.Collectors;

public class Encounter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Encounter.class);

    private final TurnFactory turnFactory;
    private final EncounterEventFactory eventFactory;
    private final EventLogger eventLogger;
    private final Collection<Character> participants;

    public Encounter(TurnFactory turnFactory, EncounterEventFactory eventFactory, EventLogger eventLogger, Character... participants) {
        this.turnFactory = turnFactory;
        this.eventFactory = eventFactory;
        this.eventLogger = eventLogger;
        this.participants = Arrays.asList(participants);
    }

    public void startEncounter() {
        LOGGER.debug("Starting encounter");
        eventLogger.logEvent(eventFactory.createEncounterStartEvent());

        // Create a list of turns, sorted by initiative
        Map<Character, Integer> charactersByInitiative = new HashMap<>();

        for(Character character : participants) {
            InitiativeRoll initiativeRoll = new InitiativeRoll();
            character.getFeatures().forEach(f -> f.onInitiativeRoll(initiativeRoll));

            charactersByInitiative.put(character, initiativeRoll.roll().getOutcome() + character.getInitiativeModifier());
        }

        List<Character> characters = charactersByInitiative.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // TODO: Change this once multiple characters allowed
        while(participants.stream().allMatch(p -> p.getHp() > 0)) {
            eventLogger.logEvent(eventFactory.createRoundStartEvent());

            for(Character character : characters) {

                // TODO: Remove instantiation here, move to factory?
                // Should a character be able to contain its own target selector?
                Turn turn = turnFactory.createTurn(character, new SimpleTargetSelector(this.getTarget(character)));

                turn.doTurn();

                // If anyone is dead, end the encounter
                if (participants.stream().anyMatch(p -> p.getHp() == 0)) {
                    break;
                }
            }
        }

        LOGGER.debug("Finishing encounter");
    }

    // TODO: Choose target based on team.
    // TODO: Allow onTargetSelect method on feature to allow e.g. cursed items to target nearest etc.
    public Character getTarget(Character forCharacter) {
        return participants.stream()
                .filter(c -> c != forCharacter)
                .findFirst()
                .orElse(null);
    }

    public void reset() {
        this.participants.forEach(Character::reset);
    }
}
