package uk.laxd.dndSimulator.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.dice.Roll;
import uk.laxd.dndSimulator.dice.RollResult;
import uk.laxd.dndSimulator.equipment.UnarmedAttack;
import uk.laxd.dndSimulator.equipment.Weapon;
import uk.laxd.dndSimulator.event.EncounterEventFactory;
import uk.laxd.dndSimulator.event.EventLogger;

import java.util.Collection;

@Component
public class DamageResolver {

    private final EncounterEventFactory eventFactory;
    private final EventLogger eventLogger;

    @Autowired
    public DamageResolver(EncounterEventFactory eventFactory, EventLogger eventLogger) {
        this.eventFactory = eventFactory;
        this.eventLogger = eventLogger;
    }

    public void resolve(AttackAction attackAction) {
        Character performer = attackAction.getPerformer();
        Character target = attackAction.getTarget();

        if (attackAction.getOutcome() == AttackOutcome.MISS) {
            eventLogger.logEvent(eventFactory.createNewMeleeAttackEvent(attackAction));
            return;
        }

        // TODO: Resolve different types of damage with vulnerabilities and resistances.

        // Resolve all pre-damage features
        performer.getFeatures().forEach(feature -> feature.onDamageRoll(attackAction));
        target.getFeatures().forEach(feature -> feature.onDamageRollReceived(attackAction));

        Weapon weapon = attackAction.getWeapon();

        if (weapon == null) {
            weapon = new UnarmedAttack();
        }

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
        for (Roll roll : damageRolls) {
            RollResult damageRollResult = roll.roll();
            attackAction.addAttackDamage(DamageType.PIERCING, damageRollResult.getOutcome());
        }

        attackAction.getTarget().applyDamage(attackAction.getAttackDamage());

        eventLogger.logEvent(eventFactory.createNewMeleeAttackEvent(attackAction));

        // Resolve all post-damage features;
        performer.getFeatures().forEach(f -> f.onDamageInflicted(attackAction));
        performer.getFeatures().forEach(f -> f.onDamageReceived(attackAction));
    }

}
