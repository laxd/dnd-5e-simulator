package uk.laxd.dndSimulator.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.laxd.dndSimulator.character.Character;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A simulation consists of a encounter repeated a number of times, with
 * outcomes returned for statistical analysis
 */
public class Simulation {

    private static final Logger LOGGER = LoggerFactory.getLogger(Simulation.class);

    public void runSimulation(Encounter encounter, int count) {
        Collection<CharacterEncounterOutcome> characterEncounterOutcomes = new ArrayList<>();

        for(int i=1; i<=count; i++) {
            encounter.reset();
            EncounterOutcome encounterOutcome = encounter.startEncounter();
            LOGGER.info("Completed simulation {}/{}", i, count);

            characterEncounterOutcomes.addAll(encounterOutcome.getCharacterEncounterOutcomes());
        }

        List<Character> characters = characterEncounterOutcomes.stream().map(CharacterEncounterOutcome::getCharacter).distinct().collect(Collectors.toList());

        Map<Character, IntSummaryStatistics> timesAttackedStatsMap = characterEncounterOutcomes.stream()
                .collect(Collectors.groupingBy(CharacterEncounterOutcome::getCharacter, Collectors.summarizingInt(CharacterEncounterOutcome::getTimesAttacked)));


        Map<Character, IntSummaryStatistics> totalHitsStatsMap = characterEncounterOutcomes.stream()
                .collect(Collectors.groupingBy(CharacterEncounterOutcome::getCharacter, Collectors.summarizingInt(CharacterEncounterOutcome::getTimesHit)));

        Map<Character, IntSummaryStatistics> damageInflictedStatsMap = characterEncounterOutcomes.stream()
                .collect(Collectors.groupingBy(CharacterEncounterOutcome::getCharacter, Collectors.summarizingInt(CharacterEncounterOutcome::getTotalDamageInflicted)));


        Map<Character, IntSummaryStatistics> turnsTakenStatsMap = characterEncounterOutcomes.stream()
                .collect(Collectors.groupingBy(CharacterEncounterOutcome::getCharacter, Collectors.summarizingInt(CharacterEncounterOutcome::getTurnsTaken)));


        for(Character character : characters) {


            LOGGER.info("{} stats:", character.getName());
            LOGGER.info("Attacks: {}", timesAttackedStatsMap.get(character));
            LOGGER.info("Hits: {}", totalHitsStatsMap.get(character));
            LOGGER.info("Hit percent: {}%", 100*(totalHitsStatsMap.get(character).getSum()/(double)timesAttackedStatsMap.get(character).getSum()));
            LOGGER.info("Damage dealt: {}", damageInflictedStatsMap.get(character));
            LOGGER.info("Damage per round: {}", damageInflictedStatsMap.get(character).getAverage()/turnsTakenStatsMap.get(character).getAverage());
            LOGGER.info("\n\n");
        }
    }

}
