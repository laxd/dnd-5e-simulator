package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.Roll
import uk.laxd.dndSimulator.dice.RollResult
import uk.laxd.dndSimulator.event.DamageEvent
import uk.laxd.dndSimulator.event.DeathEvent
import uk.laxd.dndSimulator.event.EncounterEvent
import uk.laxd.dndSimulator.event.EncounterEventType
import uk.laxd.dndSimulator.feature.Effect

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

    val events = mutableListOf<EncounterEvent>()

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

    val attackDamage = AttackDamage()

    fun applyDamage() {
        actor.features.forEach { f -> f.onDamageRoll(this) }
        actor.features.forEach { f -> f.onDamageRollReceived(this) }

        if(attackDamage.totalAmount == 0) {
            return
        }

        applyDamageModifiers()

        for(effect in attackDamage.damageMap.keys) {
            target.applyDamage(attackDamage.damageMap[effect]!!)
            events.add(DamageEvent(actor, target, attackDamage.damageMap[effect]!!, effect))
        }

        actor.features.forEach { f -> f.onDamageInflicted(this) }
        actor.features.forEach { f -> f.onDamageReceived(this) }

        if(target.isDead()) {
            events.add(DeathEvent(target, actor))
        }
    }

    private fun applyDamageModifiers() {
        val vulns = actor.features
            .flatMap { f -> f.getVulnerabilities() }
            .distinct().toMutableList()

        val resistances = actor.features
            .flatMap { f -> f.getResistances() }
            .distinct().toMutableList()

        // If something is vulnerable AND resistant to a form of
        // damage, they cancel each other out
        val both = vulns.intersect(resistances)

        vulns.removeAll(both)
        resistances.removeAll(both)

        for(effectSource in attackDamage.damageMap.keys) {
            val dmg = attackDamage.damageMap[effectSource]!!

            if(dmg.damageType in vulns) {
                attackDamage.damageMap[effectSource] = VulnerableDamage(dmg)
            }

            if(dmg.damageType in resistances) {
                attackDamage.damageMap[effectSource] = ResistantDamage(dmg)
            }
        }
    }
}