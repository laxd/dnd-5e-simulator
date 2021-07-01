package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.Roll
import uk.laxd.dndSimulator.dice.RollResult
import uk.laxd.dndSimulator.dice.RollType
import uk.laxd.dndSimulator.event.*
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

    abstract fun performAction()

    // TODO: Fix this?
    private var _modifier = AttackModifier.NORMAL

    var rollType = RollType.NORMAL_ROLL
        get() {
            return when(_modifier) {
                AttackModifier.NORMAL -> RollType.NORMAL_ROLL
                AttackModifier.ADVANTAGE -> RollType.ADVANTAGE
                AttackModifier.DISADVANTAGE -> RollType.DISADVANTAGE
                AttackModifier.BOTH -> RollType.NORMAL_ROLL
            }
        }
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
    override fun performAction() {
        // Nothing to do!
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
            target.applyDamage(
                actor,
                effect,
                attackDamage.damageMap[effect]!!)
        }

        actor.features.forEach { f -> f.onDamageInflicted(this) }
        actor.features.forEach { f -> f.onDamageReceived(this) }

        if(target.isDead()) {
            EventLogger.instance.logEvent(DeathEvent(target, actor))
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