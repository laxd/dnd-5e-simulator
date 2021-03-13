package uk.laxd.dndSimulator.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.dice.Die;

import java.util.*;
import java.util.stream.Collectors;

public class Encounter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Encounter.class);

    private int rounds;

    private Collection<Character> participants;

    private Map<Character, EncounterOutcome> outcomeMap;

    // TODO: Refactor this to take in teams? Or even a EncounterSettings object?
    public Encounter(Character... participants) {
        this.participants = Arrays.asList(participants);
    }

    public Collection<EncounterOutcome> startEncounter() {
        LOGGER.debug("Starting encounter");
        outcomeMap = new HashMap<>();
        participants.forEach(p -> outcomeMap.put(p, new EncounterOutcome(p)));

        // Create a list of turns, sorted by initiative
        List<Turn> turns = participants.stream()
                .map(p -> {
                    InitiativeRoll initiativeRoll = new InitiativeRoll();
                    p.getFeatures().forEach(f -> f.onInitiativeRoll(initiativeRoll));
                    int initiative = initiativeRoll.roll().getOutcome() + p.getInitiativeModifier();

                    return new Turn(p, this, initiative);
                })
                .sorted(Comparator.comparing(Turn::getInitiative))
                .collect(Collectors.toList());

        while(participants.stream().allMatch(p -> p.getHp() > 0)) {
            rounds++;
            for(Turn turn : turns) {
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
