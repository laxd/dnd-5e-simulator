package uk.laxd.dndSimulator.statistics

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.event.EncounterEvent
import java.util.IntSummaryStatistics
import uk.laxd.dndSimulator.event.EncounterEventType
import uk.laxd.dndSimulator.action.AttackOutcome
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.event.EventLogger
import uk.laxd.dndSimulator.event.MeleeAttackEvent
import java.util.HashMap
import java.util.function.Consumer

@Component
class StatsPrinter(private val eventLogger: EventLogger) {
    fun printStats() {
        // TODO: For now, this class only generates stats for melee attacks
        // Parse all the (attack) events to get a list of characters
        val characters = eventLogger.events
            .map { e -> e.actor }
            .distinct()
            .filterNotNull()

        // Attacks per encounter
        val attacksPerEncounter = getStats(characters,
                eventLogger.events,
                { x: EncounterEvent -> x.type == EncounterEventType.MELEE_ATTACK },
                EncounterEventType.ENCOUNTER_START,
                { 1 })

        // Hits per encounter
        val hitsPerEncounter = getStats(characters,
                eventLogger.events,
                { x -> x is MeleeAttackEvent && (x.outcome == AttackOutcome.HIT || x.outcome == AttackOutcome.CRIT) },
                EncounterEventType.ENCOUNTER_START,
                { 1 })

        // Damage by melee attacks per round
        val meleeDamagePerRound = getStats(characters,
                eventLogger.events,
                { x -> x is MeleeAttackEvent && (x.outcome == AttackOutcome.HIT || x.outcome == AttackOutcome.CRIT) },
                EncounterEventType.ROUND_START,
                { x -> (x as MeleeAttackEvent).amount.totalAmount })

        for (character in characters) {
            LOGGER.info("{} stats:", character.name)
            LOGGER.info("Attacks per encounter: {}", attacksPerEncounter[character])
            LOGGER.info("Hits per encounter: {}", hitsPerEncounter[character])
            LOGGER.info("Hit percent: {}%", 100 * (hitsPerEncounter[character]!!.sum / attacksPerEncounter[character]!!.sum.toDouble()))
            LOGGER.info("Damage dealt per round: {}", meleeDamagePerRound[character])
            LOGGER.info("\n\n")
        }
    }

    private fun getStats(characters: Collection<Character>,
                         events: Collection<EncounterEvent>,
                         filter: EncounterStatsFilter,
                         groupBy: EncounterEventType,
                         extractor: EncounterStatsExtractor): Map<Character, IntSummaryStatistics> {
        val statistics: MutableMap<Character, IntSummaryStatistics> = HashMap()
        characters.forEach(Consumer { c: Character -> statistics[c] = IntSummaryStatistics() })
        val ongoingTotal: MutableMap<Character, Int> = HashMap()
        for (event in events) {
            // Loop over events, every time something matches the filter, add it to the total.
            // Add total to statistics every time a "ROUND_START" event happens
            if (event.type == groupBy) {
                // Persist total and reset
                for (character in ongoingTotal.keys) {
                    val stats = statistics[character]
                    stats!!.accept(ongoingTotal[character]!!)
                    ongoingTotal[character] = 0
                }
            }
            if (filter.matches(event) && event.actor != null) {
                var currentTotal = ongoingTotal[event.actor] ?: 0
                
                currentTotal += extractor.getAttribute(event)
                ongoingTotal[event.actor] = currentTotal
            }
        }

        // As all events has been taken into account, make sure to flush the current ongoingTotals
        for (character in ongoingTotal.keys) {
            val stats = statistics[character]
            if (ongoingTotal[character] != 0) {
                stats!!.accept(ongoingTotal[character]!!)
                ongoingTotal[character] = 0
            }
        }
        return statistics
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(StatsPrinter::class.java)
    }
}