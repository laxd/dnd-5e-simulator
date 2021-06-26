package uk.laxd.dndSimulator.statistics

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.IntSummaryStatistics
import uk.laxd.dndSimulator.event.*

@Component
class StatsPrinter() {

    private val eventLogger = EventLogger.instance
    private val LOGGER = LoggerFactory.getLogger(this.javaClass)

    fun printStats() {
        // TODO: For now, this class only generates stats for melee attacks
        // Parse all the (attack) events to get a list of characters
        val characters = eventLogger.events
            .map { e -> e.actor }
            .distinct()
            .filterNotNull()

        for (character in characters) {
            LOGGER.info("{} stats:", character.name)

            val attacksPerEncounter = eventLogger.events
                .splitBy { e -> e.type == EncounterEventType.ENCOUNTER_START }
                .map { l -> l.filter { e -> e.actor == character && e.type == EncounterEventType.MELEE_ATTACK }.count() }
                .fold(IntSummaryStatistics()) {i, a -> i.accept(a); i}

            val hitsPerEncounter = eventLogger.events
                .splitBy { e -> e.type == EncounterEventType.ENCOUNTER_START }
                .map { l -> l.filterIsInstance<DamageEvent>().filter { e -> e.actor == character }.count() }
                .fold(IntSummaryStatistics()) {i, a -> i.accept(a); i}

            val damagePerRound = eventLogger.events
                .splitBy { e -> e.type == EncounterEventType.ROUND_START }
                .map { l -> l
                    .filterIsInstance<DamageEvent>()
                    .filter { e -> e.actor == character }
                    .sumOf { e -> e.damage.getTotalDamage() }
                }
                .fold(IntSummaryStatistics()) {i, a -> i.accept(a); i}

            LOGGER.info("Attacks per encounter: {}", attacksPerEncounter)
            LOGGER.info("Hits per encounter: {}", hitsPerEncounter)
            LOGGER.info("Hit percent: {}%", 100 * (hitsPerEncounter.sum / attacksPerEncounter.sum.toDouble()))
            LOGGER.info("Damage dealt per round: {}", damagePerRound)
            LOGGER.info("")
        }

        // Team stats
        val teams = characters.map { c -> c.team }.distinct()

        val finishEvents = eventLogger.events
            .filterIsInstance<EncounterFinishedEvent>()

        for (team in teams) {
            val wonCount = finishEvents.filter { e -> e.winningTeam == team }.count()
            val lostCount = finishEvents.filter { e -> e.winningTeam != team }.count()

            LOGGER.info("{} stats:", team)
            LOGGER.info("Times won: {}", wonCount)
            LOGGER.info("Times lost: {}", lostCount)
            LOGGER.info("Chance of winning: {}%", wonCount / (wonCount + lostCount).toDouble() * 100 )
            LOGGER.info("")
        }
    }

    fun <T> Collection<T>.splitBy(splitBy: (T) -> Boolean): Collection<Collection<T>> {
        val listOflists: MutableCollection<MutableCollection<T>> = mutableListOf()
        var currentList: MutableCollection<T> = mutableListOf()

        for(item in this) {
            if(splitBy(item) || this.last() == item) {
                listOflists.add(currentList)
                currentList = mutableListOf()
            }
            else {
                currentList.add(item)
            }
        }

        return listOflists
    }
}