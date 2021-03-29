package uk.laxd.dndSimulator.action;

import org.springframework.stereotype.Component;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.dice.Die;
import uk.laxd.dndSimulator.dice.Roll;
import uk.laxd.dndSimulator.dice.RollResult;
import uk.laxd.dndSimulator.equipment.Weapon;
import uk.laxd.dndSimulator.event.EncounterEventFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class DamageResolver {

    public void resolve(AttackAction attackAction) {
        // TODO: Resolve different types of damage with vulnerabilities and resistances.
        if (attackAction.getOutcome() == AttackOutcome.MISS) {
            // No damage on a miss
            // TODO: Add ability for e.g. burning hands spell to do 50% damage
            return;
        }

        Weapon weapon = attackAction.getWeapon();

        if(weapon == null) {
            return;
        }

        Character performer = attackAction.getPerformer();
        Character target = attackAction.getTarget();

        // Resolve all pre-damage features
        performer.getFeatures().forEach(feature -> feature.onDamageRoll(attackAction));
        target.getFeatures().forEach(feature -> feature.onDamageRollReceived(attackAction));

        // TODO: Move this to a feature? Or character.onAttack which can then add weapon damage
        Roll weaponDamageRoll = new Roll(weapon.getDamageDice(attackAction));

        // Roll damage dice
        Collection<Roll> damageRolls = attackAction.getDamageRolls();
        damageRolls.add(weaponDamageRoll);

        if (attackAction.getOutcome() == AttackOutcome.CRIT) {
            // Roll the dice again if it was a crit!
            damageRolls.addAll(damageRolls);
        }

        // TODO: Subclass Roll - DamageRoll should include type of damage
        for(Roll roll : damageRolls) {
            RollResult damageRollResult = roll.roll();
            attackAction.addAttackDamage(DamageType.PIERCING, damageRollResult.getOutcome());
        }

        attackAction.getTarget().applyDamage(attackAction.getAttackDamage());

        attackAction.addEvent(new EncounterEventFactory().createNewDamageEvent(attackAction));

        // Resolve all post-damage features;
        performer.getFeatures().forEach(f -> f.onDamageInflicted(attackAction));
        performer.getFeatures().forEach(f -> f.onDamageReceived(attackAction));
    }

}
