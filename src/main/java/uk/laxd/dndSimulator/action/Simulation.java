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

    private Collection<EncounterOutcome> encounterOutcomes = new ArrayList<>();

    public void runSimulation(Encounter encounter, int count) {
        for(int i=1; i<=count; i++) {
            encounter.reset();
            encounterOutcomes.addAll(encounter.startEncounter());
            LOGGER.info("Completed simulation {}/{}", i, count);
        }

        List<Character> characters = encounterOutcomes.stream().map(EncounterOutcome::getCharacter).distinct().collect(Collectors.toList());



        Map<Character, IntSummaryStatistics> timesAttackedStatsMap = encounterOutcomes.stream()
                .collect(Collectors.groupingBy(EncounterOutcome::getCharacter, Collectors.summarizingInt(EncounterOutcome::getTimesAttacked)));


        Map<Character, IntSummaryStatistics> remainingHpStatsMap = encounterOutcomes.stream()
                .collect(Collectors.groupingBy(EncounterOutcome::getCharacter, Collectors.summarizingInt(EncounterOutcome::getRemainingHp)));


        Map<Character, IntSummaryStatistics> totalHitsStatsMap = encounterOutcomes.stream()
                .collect(Collectors.groupingBy(EncounterOutcome::getCharacter, Collectors.summarizingInt(EncounterOutcome::getTimesHit)));

        Map<Character, IntSummaryStatistics> damageInflictedStatsMap = encounterOutcomes.stream()
                .collect(Collectors.groupingBy(EncounterOutcome::getCharacter, Collectors.summarizingInt(EncounterOutcome::getTotalDamageInflicted)));


        Map<Character, IntSummaryStatistics> turnsTakenStatsMap = encounterOutcomes.stream()
                .collect(Collectors.groupingBy(EncounterOutcome::getCharacter, Collectors.summarizingInt(EncounterOutcome::getTurnsTaken)));


        for(Character character : characters) {

            long deaths = encounterOutcomes.stream()
                    .filter(e -> e.getCharacter() == character &&  e.getRemainingHp() == 0)
                    .count();

            LOGGER.info("{} stats:", character.getName());
            LOGGER.info("Deaths: {}", deaths);
            LOGGER.info("Attacks: {}", timesAttackedStatsMap.get(character));
            LOGGER.info("Hits: {}", totalHitsStatsMap.get(character));
            LOGGER.info("Hit percent: {}%", 100*(totalHitsStatsMap.get(character).getSum()/(double)timesAttackedStatsMap.get(character).getSum()));
            LOGGER.info("Damage dealt: {}", damageInflictedStatsMap.get(character));
            LOGGER.info("Damage per round: {}", damageInflictedStatsMap.get(character).getAverage()/turnsTakenStatsMap.get(character).getAverage());
            LOGGER.info("\n\n");
        }
    }

}
