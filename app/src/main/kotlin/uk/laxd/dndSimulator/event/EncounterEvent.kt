package uk.laxd.dndSimulator.event

import uk.laxd.dndSimulator.action.AttackOutcome
import uk.laxd.dndSimulator.action.Damage
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.RollResult
import uk.laxd.dndSimulator.feature.Effect

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

class TurnEndEvent(actor: Character) : EncounterEvent(actor, EncounterEventType.TURN_END, "$actor turn finished")

class RoundStartEvent : EncounterEvent(null, EncounterEventType.ROUND_START, "New round started")

class EncounterStartEvent : EncounterEvent(null, EncounterEventType.ENCOUNTER_START, "New encounter started")

class DeathEvent(character: Character, killedBy: Character) : EncounterEvent(character, EncounterEventType.DEATH, "$killedBy killed $character!")

class EncounterFinishedEvent(val winningTeam : String) : EncounterEvent(null, EncounterEventType.ENCOUNTER_FINISH, "$winningTeam won!")

class MeleeAttackEventOnly(character: Character, val target: Character, val rollResult: RollResult, val outcome: AttackOutcome) :
    EncounterEvent(character, EncounterEventType.MELEE_ATTACK, "$character attacked $target - $rollResult - $outcome")

class DamageEvent(character: Character, val target: Character, val damage: Damage, val effect: Effect) :
        EncounterEvent(character, EncounterEventType.DAMAGE, "$target took $damage damage from $character's $effect")
