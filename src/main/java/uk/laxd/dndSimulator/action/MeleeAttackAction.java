package uk.laxd.dndSimulator.action;

import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.dice.Die;
import uk.laxd.dndSimulator.dice.Roll;
import uk.laxd.dndSimulator.dice.RollResult;
import uk.laxd.dndSimulator.equipment.UnarmedAttack;
import uk.laxd.dndSimulator.equipment.Weapon;
import uk.laxd.dndSimulator.event.EncounterEvent;
import uk.laxd.dndSimulator.event.EncounterEventType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MeleeAttackAction implements Action {

    private Character performer;
    private Character target;
    private Weapon weapon;

    // Don't like this, but as they are mutually exclusive, can't be set via decorator
    private boolean withAdvantage;
    private boolean withDisadvantage;

    private AttackOutcome outcome;

    private Roll attackRoll;
    private List<Roll> damageRolls = new ArrayList<>();

    private RollResult attackRollResult;
    private Damage attackDamage = new Damage();

    private Collection<EncounterEvent> events = new ArrayList<>();

    public MeleeAttackAction(Character performer, Weapon weapon, Character target) {
        this.performer = performer;
        this.weapon = weapon;
        this.target = target;

        this.attackRoll = new Roll(Die.D20);
    }

    public Character getPerformer() {
        return performer;
    }

    public Character getTarget() {
        return target;
    }

    public Weapon getWeapon() {
        return weapon;
    }


    public Roll getAttackRoll() {
        return attackRoll;
    }

    public void setAttackRoll(Roll attackRoll) {
        this.attackRoll = attackRoll;
    }


    public List<Roll> getDamageRolls() {
        return damageRolls;
    }

    public void addDamageRolls(Roll damageRoll) {
        this.damageRolls.add(damageRoll);
    }

    public AttackOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(AttackOutcome outcome) {
        this.outcome = outcome;
    }

    public RollResult getAttackRollResult() {
        return attackRollResult;
    }

    public void setAttackRollResult(RollResult attackRollResult) {
        this.attackRollResult = attackRollResult;
    }

    public Damage getAttackDamage() {
        return attackDamage;
    }

    public void addAttackDamage(DamageType type, int amount) {
        attackDamage.addAmount(type, amount);
    }

    public boolean isWithAdvantage() {
        return withDisadvantage && withAdvantage ? false : withAdvantage;
    }

    public void setWithAdvantage(boolean withAdvantage) {
        this.withAdvantage = withAdvantage;
    }

    public boolean isWithDisadvantage() {
        return withDisadvantage && withAdvantage ? false : withDisadvantage;
    }

    public void setWithDisadvantage(boolean withDisadvantage) {
        this.withDisadvantage = withDisadvantage;
    }

    public void addEvent(EncounterEvent event) {
        this.events.add(event);
    }

    public Collection<EncounterEvent> getEvents() {
        return new ArrayList<>(events);
    }

    @Override
    public String toString() {
        return String.format("%s attacked %s (%s) - %s for %s", performer, target, attackRoll, outcome, attackDamage);
    }

    @Override
    public void performAction() {
        if (weapon == null) {
            weapon = new UnarmedAttack();
        }

        // Resolve all features
        performer.getFeatures().forEach(feature -> feature.onAttackRoll(this));
        target.getFeatures().forEach(feature -> feature.onAttackRollReceiving(this));

        // See if the attack hits
        // Roll the dice
        RollResult rollResult = getAttackRoll().roll();

        if (isWithAdvantage()) {
            RollResult rollResult2 = getAttackRoll().roll();

            // Replace roll if advantage roll was greater than the first roll
            if(rollResult2.getOutcome() > rollResult.getOutcome()) {
                rollResult = rollResult2;
            }
        }
        else if(isWithDisadvantage()) {
            RollResult rollResult2 = getAttackRoll().roll();

            // Replace roll if advantage roll was greater than the first roll
            if(rollResult2.getOutcome() < rollResult.getOutcome()) {
                rollResult = rollResult2;
            }
        }

        setAttackRollResult(rollResult);

        if(rollResult.getDieOutcome() == 1) {
            setOutcome(AttackOutcome.MISS);
        }
        else if(rollResult.getDieOutcome() == 20) {
            setOutcome(AttackOutcome.CRIT);
        }


        // If it wasn't a crit or a crit fail, see if it hits
        if(getOutcome() == null) {
            if(rollResult.getOutcome() >= target.getArmorClass()) {
                setOutcome(AttackOutcome.HIT);
            }
            else {
                setOutcome(AttackOutcome.MISS);
            }
        }

        if (getOutcome() == AttackOutcome.MISS) {
            // TODO: eventLogger.logEvent(eventFactory.createNewMeleeAttackEvent(attackAction));
            return;
        }

        // TODO: Resolve different types of damage with vulnerabilities and resistances.

        // Resolve all pre-damage features
        performer.getFeatures().forEach(feature -> feature.onDamageRoll(this));
        target.getFeatures().forEach(feature -> feature.onDamageRollReceived(this));

        // TODO: Move this to a feature? Or character.onAttack which can then add weapon damage
        Roll weaponDamageRoll = new Roll(weapon.getDamageDice(this));

        // Roll damage dice
        Collection<Roll> damageRolls = getDamageRolls();
        damageRolls.add(weaponDamageRoll);

        if (getOutcome() == AttackOutcome.CRIT) {
            // Roll the dice again if it was a crit!
            damageRolls.addAll(damageRolls);
        }

        // TODO: Subclass Roll - DamageRoll should include type of damage
        for (Roll roll : damageRolls) {
            RollResult damageRollResult = roll.roll();
            addAttackDamage(DamageType.PIERCING, damageRollResult.getOutcome());
        }

        getTarget().applyDamage(getAttackDamage());


        // Resolve all post-damage features;
        performer.getFeatures().forEach(f -> f.onDamageInflicted(this));
        performer.getFeatures().forEach(f -> f.onDamageReceived(this));
    }

    @Override
    public EncounterEventType getEventType() {
        return EncounterEventType.MELEE_ATTACK;
    }
}
