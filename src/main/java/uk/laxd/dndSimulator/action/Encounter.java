package uk.laxd.dndSimulator.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.laxd.dndSimulator.character.Character;

import java.util.*;
import java.util.stream.Collectors;

public class Encounter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Encounter.class);

    private int rounds;

    private ActionResolver actionResolver;
    private DamageResolver damageResolver;

    private Collection<Character> participants;

    private EncounterOutcome outcome;

    public Encounter(ActionResolver actionResolver, DamageResolver damageResolver, Character... participants) {
        this.actionResolver = actionResolver;
        this.damageResolver = damageResolver;
        this.participants = Arrays.asList(participants);
    }

    public EncounterOutcome startEncounter() {
        LOGGER.debug("Starting encounter");
        outcome = new EncounterOutcome();

        participants.forEach(p -> outcome.addParticipant(p));

        // Create a list of turns, sorted by initiative
        Map<Character, Integer> charactersByInitiative = new HashMap<>();

        for(Character character : participants) {
            InitiativeRoll initiativeRoll = new InitiativeRoll();
            character.getFeatures().forEach(f -> f.onInitiativeRoll(initiativeRoll));

            charactersByInitiative.put(character, initiativeRoll.roll().getOutcome() + character.getInitiativeModifier());
        }

        List<Character> characters = charactersByInitiative.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // TODO: Change this once multiple characters allowed
        while(participants.stream().allMatch(p -> p.getHp() > 0)) {
            // TODO: Add RoundStatistics entity? I.e. so that HP changes can be graphed
            rounds++;
            for(Character character : characters) {

                // TODO: Remove instantiation here, move to factory?
                // Should a character be able to contain its own target selector?
                Turn turn = new Turn(actionResolver, damageResolver, character, new SimpleTargetSelector(this.getTarget(character)));

                TurnOutcome turnOutcome = turn.doTurn();

                // Update the encounter outcome
                CharacterEncounterOutcome characterEncounterOutcome = outcome.getCharacterEncounterOutcome(turn.getCharacter());

                characterEncounterOutcome.incrementTurnsTaken();
                characterEncounterOutcome.incrementTimesAttacked();
                characterEncounterOutcome.incrementTotalDamageInflicted(turnOutcome.getDamage());
                if(turnOutcome.isHit()) {
                    characterEncounterOutcome.incrementTimesHit();
                }

                characterEncounterOutcome.addEvents(turnOutcome.getEvents());

                if (participants.stream().anyMatch(p -> p.getHp() == 0)) {
                    break;
                }
            }
        }

        LOGGER.debug("Finishing encounter");

        return outcome;
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
        this.rounds = 0;
        this.participants.forEach(p -> p.reset());
    }

    public int getRounds() {
        return rounds;
    }
}
