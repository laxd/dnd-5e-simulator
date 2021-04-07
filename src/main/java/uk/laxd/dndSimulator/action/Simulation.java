package uk.laxd.dndSimulator.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.event.EncounterEvent;
import uk.laxd.dndSimulator.event.EncounterEventType;
import uk.laxd.dndSimulator.event.EventOutcome;
import uk.laxd.dndSimulator.statistics.EncounterStatsExtractor;
import uk.laxd.dndSimulator.statistics.EncounterStatsFilter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A simulation consists of a encounter repeated a number of times, with
 * outcomes returned for statistical analysis
 */
public class Simulation {

    private Collection<Character> characters;

    private static final Logger LOGGER = LoggerFactory.getLogger(Simulation.class);

    public void runSimulation(Encounter encounter, int count) {
        Collection<EncounterOutcome> encounterOutcomes = new ArrayList<>();

        // Store list of participants for use in stats
        characters = encounter.getParticipants();

        for(int i=1; i<=count; i++) {
            encounter.reset();
            EncounterOutcome encounterOutcome = encounter.startEncounter();
            LOGGER.info("Completed simulation {}/{}", i, count);

            encounterOutcomes.add(encounterOutcome);
        }

        // Attacks per encounter
        Map<Character, IntSummaryStatistics> attacksPerEncounter = getStats(encounterOutcomes,
                x -> x.getType() == EncounterEventType.MELEE_ATTACK,
                EncounterEventType.ENCOUNTER_START,
                x -> 1);

        // Hits per encounter
        Map<Character, IntSummaryStatistics> hitsPerEncounter = getStats(encounterOutcomes,
                x -> x.getType() == EncounterEventType.MELEE_ATTACK && (x.getEventOutcome() == AttackOutcome.HIT || x.getEventOutcome() == AttackOutcome.CRIT),
                EncounterEventType.ENCOUNTER_START,
                x -> 1);

        // Damage by melee attacks per round
        Map<Character, IntSummaryStatistics> meleeDamagePerRound = getStats(encounterOutcomes,
                x -> x.getType() == EncounterEventType.MELEE_ATTACK && (x.getEventOutcome() == AttackOutcome.HIT || x.getEventOutcome() == AttackOutcome.CRIT),
                EncounterEventType.ROUND_START,
                x -> x.getAmount().getTotalAmount());

        for(Character character : characters) {
            LOGGER.info("{} stats:", character.getName());
            LOGGER.info("Attacks per encounter: {}", attacksPerEncounter.get(character));
            LOGGER.info("Hits per encounter: {}", hitsPerEncounter.get(character));
            LOGGER.info("Hit percent: {}%", 100*(hitsPerEncounter.get(character).getSum()/(double)attacksPerEncounter.get(character).getSum()));
            LOGGER.info("Damage dealt per round: {}", meleeDamagePerRound.get(character));
            LOGGER.info("\n\n");
        }

    }

    public Map<Character, IntSummaryStatistics> getStats(Collection<EncounterOutcome> encounterOutcomes,
                                                 EncounterStatsFilter filter,
                                                 EncounterEventType groupBy,
                                                 EncounterStatsExtractor extractor) {
        Map<Character, IntSummaryStatistics> statistics = new HashMap<>();

        this.characters.forEach(c -> statistics.put(c, new IntSummaryStatistics()));

        for(EncounterOutcome outcome : encounterOutcomes) {
            // Loop over events, every time something matches the filter, add it to the total.
            // Add total to statistics every time a "ROUND_START" event happens

            Map<Character, Integer> ongoingTotal = new HashMap<>();

            for(EncounterEvent event : outcome.getEvents()) {
                if(event.getType() == groupBy) {
                    // Persist total and reset
                    for(Character character : ongoingTotal.keySet()) {
                        IntSummaryStatistics stats = statistics.get(character);
                        stats.accept(ongoingTotal.get(character));

                        ongoingTotal.put(character, 0);
                    }
                }

                if(filter.matches(event)) {
                    int currentTotal = ongoingTotal.getOrDefault(event.getActor(), 0);
                    currentTotal += extractor.getAttribute(event);

                    ongoingTotal.put(event.getActor(), currentTotal);
                }
            }

            for(Character character : ongoingTotal.keySet()) {
                IntSummaryStatistics stats = statistics.get(character);

                if(ongoingTotal.get(character) != 0) {
                    stats.accept(ongoingTotal.get(character));

                    ongoingTotal.put(character, 0);
                }
            }
        }

        return statistics;
    }

}
