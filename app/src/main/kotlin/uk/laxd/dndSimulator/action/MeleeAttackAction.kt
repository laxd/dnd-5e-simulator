package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.equipment.Weapon
import uk.laxd.dndSimulator.action.AttackOutcome
import uk.laxd.dndSimulator.action.Damage
import uk.laxd.dndSimulator.event.EncounterEvent
import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.*
import uk.laxd.dndSimulator.equipment.UnarmedAttack
import uk.laxd.dndSimulator.equipment.WeaponProperty
import uk.laxd.dndSimulator.event.EncounterEventType
import uk.laxd.dndSimulator.feature.Feature
import java.util.ArrayList
import java.util.function.Consumer
import kotlin.math.max

class MeleeAttackAction(
    actor: Character,
    var weapon: Weapon,
    target: Character
) : AttackAction(actor, target, EncounterEventType.MELEE_ATTACK) {

    override fun toString(): String {
        return String.format("%s attacked %s (%s) - %s for %s", actor, target, attackRollResult, outcome, attackDamage)
    }

    override fun performAction() {
        val attackRoll = createAttackRoll()

        attackRollResult = attackRoll.roll()

        outcome = when(attackRollResult.dieOutcome) {
            1 -> AttackOutcome.MISS
            20 -> AttackOutcome.CRIT
            in (target.armorClass..19) -> AttackOutcome.HIT
            else -> AttackOutcome.MISS
        }

        if (outcome == AttackOutcome.MISS) {
            return
        }

        // TODO: Resolve different types of damage with vulnerabilities and resistances.
        val weaponDamageRoll = createDamageRoll()

        if (outcome == AttackOutcome.CRIT) {
            // Roll the dice again if it was a crit
            weaponDamageRoll.dice.addAll(weaponDamageRoll.dice);
        }

        // TODO: Subclass Roll - DamageRoll should include type of damage
        damageRollResult = weaponDamageRoll.roll()
        attackDamage.addAmount(weapon.damageType, damageRollResult.outcome)
    }

    private fun createAttackRoll(): Roll {
        var mod = weapon.getToHitModifier(this)

        // TODO: Ranged weapons
        mod += if(weapon.hasProperty(WeaponProperty.FINESSE)) {
            max(actor.getAbilityModifier(Ability.DEXTERITY), actor.getAbilityModifier(Ability.STRENGTH))
        }
        else {
            actor.getAbilityModifier(Ability.STRENGTH)
        }

        // TODO: Check if character is proficient with weapon
        mod += actor.proficiencyBonus

        return when {
            withAdvantage -> AdvantageRoll(Roll(Die.D20, modifier = mod))
            withDisadvantage -> DisadvantageRoll(Roll(Die.D20, modifier = mod))
            else -> Roll(Die.D20, modifier = mod)
        }
    }

    private fun createDamageRoll(): Roll {
        var mod = if(weapon.hasProperty(WeaponProperty.FINESSE)) {
            max(actor.getAbilityModifier(Ability.DEXTERITY), actor.getAbilityModifier(Ability.STRENGTH))
        }
        else {
            actor.getAbilityModifier(Ability.STRENGTH)
        }

        mod += weapon.getDamage(this)

        return Roll(weapon.getDamageDice(this), mod)
    }

}