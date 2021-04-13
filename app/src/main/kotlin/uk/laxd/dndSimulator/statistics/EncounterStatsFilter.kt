package uk.laxd.dndSimulator.statistics

import uk.laxd.dndSimulator.event.EncounterEvent

fun interface EncounterStatsFilter {
    fun matches(event: EncounterEvent): Boolean
}