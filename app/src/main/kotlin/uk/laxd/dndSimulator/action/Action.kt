package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.Roll
import uk.laxd.dndSimulator.dice.RollResult
import uk.laxd.dndSimulator.event.EncounterEvent
import uk.laxd.dndSimulator.event.EncounterEventType
import java.util.ArrayList

/**
 * An action represents any action that a player (or creature) may take, and
 * includes, but is not limited to:
 *
 *  1. An action
 *  2. A bonus action
 *  3. Movement
 *  4. A free action
 *  5. Reactions
 *
 */
abstract class Action(
    val actor: Character,
    val eventType: EncounterEventType
) {

    abstract fun performAction() : Collection<EncounterEvent>

    // TODO: Fix this?
    private var _modifier = AttackModifier.NORMAL

    // Don't like this, but as they are mutually exclusive, can't be set via decorator
    var withAdvantage = false
        get() = _modifier == AttackModifier.ADVANTAGE
        private set

    var withDisadvantage = false
        get() = _modifier == AttackModifier.DISADVANTAGE
        private set

    fun withAdvantage() {
        this._modifier = when(_modifier) {
            AttackModifier.DISADVANTAGE -> AttackModifier.BOTH
            AttackModifier.BOTH -> AttackModifier.BOTH
            AttackModifier.NORMAL -> AttackModifier.ADVANTAGE
            AttackModifier.ADVANTAGE -> AttackModifier.ADVANTAGE
        }
    }

    fun withDisadvantage() {
        this._modifier = when(_modifier) {
            AttackModifier.DISADVANTAGE -> AttackModifier.DISADVANTAGE
            AttackModifier.BOTH -> AttackModifier.BOTH
            AttackModifier.NORMAL -> AttackModifier.DISADVANTAGE
            AttackModifier.ADVANTAGE -> AttackModifier.BOTH
        }
    }
}

class NoOpAction(actor: Character, eventType: EncounterEventType): Action(actor, eventType) {
    override fun performAction(): Collection<EncounterEvent> {
        // Nothing to do!
        return listOf()
    }
}

abstract class AttackAction(
    actor: Character,
    val target: Character,
    eventType: EncounterEventType
): Action(actor, eventType) {
    val actionResult = ActionResult()
    var outcome: AttackOutcome = AttackOutcome.NOT_PERFORMED

    var attackRoll: Roll? = null
    var attackRollResult: RollResult = RollResult()
    var damageRoll: Roll? = null
    var damageRollResult: RollResult = RollResult()

    val attackDamage = Damage()
}