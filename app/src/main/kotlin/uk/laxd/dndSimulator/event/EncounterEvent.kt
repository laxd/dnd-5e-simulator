package uk.laxd.dndSimulator.event

import uk.laxd.dndSimulator.feature.Effect
import uk.laxd.dndSimulator.equipment.Weapon
import uk.laxd.dndSimulator.action.Damage
import uk.laxd.dndSimulator.action.AttackOutcome
import uk.laxd.dndSimulator.character.Character

/**
 * A collection of EncounterEvents can be used to describe an entire encounter
 * and may contain:
 *
 * Attacks
 *
 */
// TODO: Subclass this with DamageEvent, HealingEvent, ItemUseEvent etc
class EncounterEvent : Comparable<EncounterEvent> {
    // Index of the event
    var index = 0

    // What happened
    @JvmField
    var type: EncounterEventType? = null

    // With what
    @JvmField
    var weapon: Weapon? = null

    // How much
    @JvmField
    var amount: Damage? = null

    // Why did it happen?
    var effect: Effect? = null

    // Who did it
    @JvmField
    var actor: Character? = null

    // To who
    @JvmField
    var target: Character? = null

    // Did it work? (TODO: Generalise)
    @JvmField
    var eventOutcome: AttackOutcome? = null

    override fun toString(): String {
        return "$actor attacked $target with $weapon dealing $amount damage"
    }

    override fun compareTo(other: EncounterEvent): Int {
        return index - other.index
    }
}