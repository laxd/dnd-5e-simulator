package uk.laxd.dndSimulator.event

import uk.laxd.dndSimulator.character.Character

/**
 * A collection of EncounterEvents can be used to describe an entire encounter
 * and may contain:
 *
 * - Melee attacks
 * - Spells
 * - Movement
 * - Generic events (Encounter start, turn start, deaths, etc)
 *
 */
// TODO: Subclass this with DamageEvent, HealingEvent, ItemUseEvent etc
open class EncounterEvent(
    val actor: Character?,
    val type: EncounterEventType,
    val string: String
) {
    override fun toString() = string
}

class GeneralEncounterEvent(actor: Character, type: EncounterEventType) : EncounterEvent(actor, type, "$type event")

class TurnStartEvent(actor: Character) : EncounterEvent(actor, EncounterEventType.TURN_START, "$actor turn started")

class RoundStartEvent : EncounterEvent(null, EncounterEventType.ROUND_START, "New round started")

class EncounterStartEvent : EncounterEvent(null, EncounterEventType.ENCOUNTER_START, "New encounter started")

class DeathEvent(character: Character, val killedBy: Character) : EncounterEvent(character, EncounterEventType.DEATH, "$character was killed by $killedBy")