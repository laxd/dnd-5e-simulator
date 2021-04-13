package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.equipment.Weapon
import uk.laxd.dndSimulator.action.AttackOutcome
import uk.laxd.dndSimulator.dice.Roll
import uk.laxd.dndSimulator.dice.RollResult
import uk.laxd.dndSimulator.action.Damage
import uk.laxd.dndSimulator.event.EncounterEvent
import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.equipment.UnarmedAttack
import uk.laxd.dndSimulator.event.EncounterEventType
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.feature.Feature
import java.util.ArrayList
import java.util.function.Consumer

class MeleeAttackAction(val performer: Character, var weapon: Weapon, val target: Character) : Action {

    // TODO: Fix this?
    private var _modifier = AttackModifier.NORMAL

    // Don't like this, but as they are mutually exclusive, can't be set via decorator
    var withAdvantage = false
        get() = _modifier == AttackModifier.ADVANTAGE
        private set

    var withDisadvantage = false
        get() = _modifier == AttackModifier.DISADVANTAGE
        private set

    var outcome: AttackOutcome? = null
    var attackRoll: Roll
    val damageRolls: MutableList<Roll> = ArrayList()
    var attackRollResult: RollResult? = null
    val attackDamage = Damage()

    private val events: MutableCollection<EncounterEvent> = ArrayList()

    fun addDamageRolls(damageRoll: Roll) {
        damageRolls.add(damageRoll)
    }

    fun addAttackDamage(type: DamageType, amount: Int) {
        attackDamage.addAmount(type, amount)
    }

    fun addEvent(event: EncounterEvent) {
        events.add(event)
    }

    fun getEvents(): Collection<EncounterEvent> {
        return ArrayList(events)
    }

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
        }    }

    override fun toString(): String {
        return String.format("%s attacked %s (%s) - %s for %s", performer, target, attackRoll, outcome, attackDamage)
    }

    override fun performAction() {
        // Resolve all features
        performer.features.forEach(Consumer { feature: Feature -> feature.onAttackRoll(this) })
        target.features.forEach(Consumer { feature: Feature -> feature.onAttackRollReceiving(this) })

        // See if the attack hits
        // Roll the dice
        var rollResult = attackRoll.roll()
        if (withAdvantage) {
            val rollResult2 = attackRoll.roll()

            // Replace roll if advantage roll was greater than the first roll
            if (rollResult2.outcome > rollResult.outcome) {
                rollResult = rollResult2
            }
        } else if (withDisadvantage) {
            val rollResult2 = attackRoll.roll()

            // Replace roll if advantage roll was greater than the first roll
            if (rollResult2.outcome < rollResult.outcome) {
                rollResult = rollResult2
            }
        }
        attackRollResult = rollResult
        if (rollResult.dieOutcome == 1) {
            outcome = AttackOutcome.MISS
        } else if (rollResult.dieOutcome == 20) {
            outcome = AttackOutcome.CRIT
        }


        // If it wasn't a crit or a crit fail, see if it hits
        if (outcome == null) {
            outcome = if (rollResult.outcome >= target.armorClass) {
                AttackOutcome.HIT
            } else {
                AttackOutcome.MISS
            }
        }
        if (outcome == AttackOutcome.MISS) {
            return
        }

        // TODO: Resolve different types of damage with vulnerabilities and resistances.

        // Resolve all pre-damage features
        performer.features.forEach(Consumer { feature: Feature -> feature.onDamageRoll(this) })
        target.features.forEach(Consumer { feature: Feature -> feature.onDamageRollReceived(this) })

        // TODO: Move this to a feature? Or character.onAttack which can then add weapon damage
        val weaponDamageRoll = Roll(weapon.getDamageDice(this))

        // Roll damage dice
        val damageRolls: MutableCollection<Roll> = damageRolls
        damageRolls.add(weaponDamageRoll)

        if (outcome == AttackOutcome.CRIT) {
            // Roll the dice again if it was a crit!
            damageRolls.addAll(damageRolls)
        }

        // TODO: Subclass Roll - DamageRoll should include type of damage
        for (roll in damageRolls) {
            val damageRollResult = roll.roll()
            addAttackDamage(DamageType.PIERCING, damageRollResult.outcome)
        }
        target.applyDamage(attackDamage)

        // Resolve all post-damage features;
        performer.features.forEach(Consumer { f: Feature -> f.onDamageInflicted(this) })
        performer.features.forEach(Consumer { f: Feature -> f.onDamageReceived(this) })
    }

    override val eventType: EncounterEventType
        get() = EncounterEventType.MELEE_ATTACK

    init {
        attackRoll = Roll(Die.D20)
    }
}