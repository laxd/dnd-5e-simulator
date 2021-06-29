package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.equipment.Weapon
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.*
import uk.laxd.dndSimulator.equipment.WeaponProperty
import uk.laxd.dndSimulator.event.*
import kotlin.math.max

class MeleeAttackAction(
    val attackRollFactory: WeaponAttackRollFactory,
    actor: Character,
    var weapon: Weapon,
    target: Character
) : AttackAction(actor, target, EncounterEventType.MELEE_ATTACK) {

    override fun performAction() {
        val attackRoll = attackRollFactory.createAttackRoll(weapon, actor, withAdvantage, withDisadvantage)

        actor.features.forEach { f -> f.onAttackRoll(this) }
        target.features.forEach { f -> f.onAttackRollReceiving(this) }

        attackRollResult = attackRoll.roll()

        outcome = when(attackRollResult.dieOutcome) {
            1 -> AttackOutcome.MISS
            20 -> AttackOutcome.CRIT
            else -> if (attackRollResult.outcome >= target.armorClass) AttackOutcome.HIT else AttackOutcome.MISS
        }

        EventLogger.instance.logEvent(MeleeAttackEventOnly(actor, target, attackRollResult, outcome))

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
        attackDamage.addDamage(weapon, weapon.damageType, damageRollResult)

        applyDamage()
    }

    // TODO: Move to WeaponAttackDamageRollFactory
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