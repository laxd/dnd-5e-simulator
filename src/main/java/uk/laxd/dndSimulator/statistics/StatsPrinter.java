package uk.laxd.dndSimulator.statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.laxd.dndSimulator.action.AttackOutcome;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.event.EncounterEvent;
import uk.laxd.dndSimulator.event.EncounterEventType;
import uk.laxd.dndSimulator.event.EventLogger;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class StatsPrinter {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatsPrinter.class);

    private final EventLogger eventLogger;

    @Autowired
    public StatsPrinter(EventLogger eventLogger) {
        this.eventLogger = eventLogger;
    }

    public void printStats() {
        // Parse all the events to get a list of characters
        Collection<Character> characters = eventLogger.getEvents().stream()
                .map(EncounterEvent::getActor)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // Attacks per encounter
        Map<Character, IntSummaryStatistics> attacksPerEncounter = getStats(characters,
                eventLogger.getEvents(),
                x -> x.getType() == EncounterEventType.MELEE_ATTACK,
                EncounterEventType.ENCOUNTER_START,
                x -> 1);

        // Hits per encounter
        Map<Character, IntSummaryStatistics> hitsPerEncounter = getStats(characters,
                eventLogger.getEvents(),
                x -> x.getType() == EncounterEventType.MELEE_ATTACK && (x.getEventOutcome() == AttackOutcome.HIT || x.getEventOutcome() == AttackOutcome.CRIT),
                EncounterEventType.ENCOUNTER_START,
                x -> 1);

        // Damage by melee attacks per round
        Map<Character, IntSummaryStatistics> meleeDamagePerRound = getStats(characters,
                eventLogger.getEvents(),
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


    private Map<Character, IntSummaryStatistics> getStats(Collection<Character> characters,
                                                         Collection<EncounterEvent> events,
                                                         EncounterStatsFilter filter,
                                                         EncounterEventType groupBy,
                                                         EncounterStatsExtractor extractor) {
        Map<Character, IntSummaryStatistics> statistics = new HashMap<>();

        characters.forEach(c -> statistics.put(c, new IntSummaryStatistics()));

        Map<Character, Integer> ongoingTotal = new HashMap<>();

        for(EncounterEvent event : events) {
            // Loop over events, every time something matches the filter, add it to the total.
            // Add total to statistics every time a "ROUND_START" event happens

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

        // As all events has been taken into account, make sure to flush the current ongoingTotals
        for(Character character : ongoingTotal.keySet()) {
            IntSummaryStatistics stats = statistics.get(character);

            if(ongoingTotal.get(character) != 0) {
                stats.accept(ongoingTotal.get(character));

                ongoingTotal.put(character, 0);
            }
        }

        return statistics;
    }

}
