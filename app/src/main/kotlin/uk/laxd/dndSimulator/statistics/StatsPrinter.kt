package uk.laxd.dndSimulator.statistics

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.IntSummaryStatistics
import uk.laxd.dndSimulator.action.AttackOutcome
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.event.*
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

        for (character in characters) {
            LOGGER.info("{} stats:", character.name)

            // Attacks per encounter
            val attacksPerEncounter = getStats(character,
                eventLogger.events,
                { x: EncounterEvent -> x.type == EncounterEventType.MELEE_ATTACK },
                EncounterEventType.ENCOUNTER_START,
                { 1 })

            // Hits per encounter
            val hitsPerEncounter = getStats(character,
                eventLogger.events,
                { x -> x is MeleeAttackEvent && (x.outcome == AttackOutcome.HIT || x.outcome == AttackOutcome.CRIT) },
                EncounterEventType.ENCOUNTER_START,
                { 1 })

            // Damage by melee attacks per round
            val meleeDamagePerRound = getStats(character,
                eventLogger.events,
                { x -> x is MeleeAttackEvent && (x.outcome == AttackOutcome.HIT || x.outcome == AttackOutcome.CRIT) },
                EncounterEventType.ROUND_START,
                { x -> (x as MeleeAttackEvent).amount.totalAmount })

            LOGGER.info("Attacks per encounter: {}", attacksPerEncounter)
            LOGGER.info("Hits per encounter: {}", hitsPerEncounter)
            LOGGER.info("Hit percent: {}%", 100 * (hitsPerEncounter.sum / attacksPerEncounter.sum.toDouble()))
            LOGGER.info("Damage dealt per round: {}", meleeDamagePerRound)
            LOGGER.info("\n")
        }

        // Team stats
        val teams = characters.map { c -> c.team }.distinct()

        val finishEvents = eventLogger.events
            .filterIsInstance<EncounterFinishedEvent>()

        for (team in teams) {
            LOGGER.info("{} stats:", team)
            LOGGER.info("Times won: {}", finishEvents.filter { e -> e.winningTeam == team }.count())
            LOGGER.info("Times lost: {}", finishEvents.filter { e -> e.winningTeam != team }.count())
        }
    }

    private fun getStats(character: Character,
                         events: Collection<EncounterEvent>,
                         filter: EncounterStatsFilter,
                         groupBy: EncounterEventType,
                         extractor: EncounterStatsExtractor): IntSummaryStatistics {
        val stats = IntSummaryStatistics()
        var count = 0

        for (event in events) {
            // Loop over events, every time something matches the filter, add it to the total.
            // Add total to statistics every time a "ROUND_START" event happens
            if (event.type == groupBy) {
                stats.accept(count);
                count = 0
            }

            if (filter.matches(event) && event.actor == character) {
                count += extractor.getAttribute(event)
            }
        }

        // As all events has been taken into account, make sure to flush the current ongoingTotals
        if (count != 0) {
            stats.accept(count)
        }

        return stats
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(StatsPrinter::class.java)
    }
}