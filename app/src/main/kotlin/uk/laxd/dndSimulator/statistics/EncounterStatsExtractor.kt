package uk.laxd.dndSimulator.statistics

import uk.laxd.dndSimulator.event.EncounterEvent

fun interface EncounterStatsExtractor {
    fun getAttribute(event: EncounterEvent): Int
}