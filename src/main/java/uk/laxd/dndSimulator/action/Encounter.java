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

    private Map<Character, EncounterOutcome> outcomeMap;

    // TODO: Refactor this to take in teams? Or even a EncounterSettings object?
    public Encounter(ActionResolver actionResolver, DamageResolver damageResolver, Character... participants) {
        this.actionResolver = actionResolver;
        this.damageResolver = damageResolver;
        this.participants = Arrays.asList(participants);
    }

    public Collection<EncounterOutcome> startEncounter() {
        LOGGER.debug("Starting encounter");
        outcomeMap = new HashMap<>();
        participants.forEach(p -> outcomeMap.put(p, new EncounterOutcome(p)));

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


        while(participants.stream().allMatch(p -> p.getHp() > 0)) {
            rounds++;
            for(Character character : characters) {

                // TODO: Remove instantiation here, move to factory?
                // Should a character be able to contain its own target selector?
                Turn turn = new Turn(actionResolver, damageResolver, character, new SimpleTargetSelector(this.getTarget(character)));

                TurnOutcome turnOutcome = turn.doTurn();

                // Update the encounter outcome
                EncounterOutcome encounterOutcome = outcomeMap.get(turn.getCharacter());

                encounterOutcome.incrementTurnsTaken();
                encounterOutcome.incrementTimesAttacked();
                encounterOutcome.incrementTotalDamageInflicted(turnOutcome.getDamage());
                if(turnOutcome.isHit()) {
                    encounterOutcome.incrementTimesHit();
                }

                if (participants.stream().anyMatch(p -> p.getHp() == 0)) {
                    break;
                }
            }
        }

        participants.forEach(p -> outcomeMap.get(p).setRemainingHp(p.getHp()));

        LOGGER.debug("Finishing encounter");

        return outcomeMap.values();
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
