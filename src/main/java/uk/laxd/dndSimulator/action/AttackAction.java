package uk.laxd.dndSimulator.action;

import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.dice.Die;
import uk.laxd.dndSimulator.dice.Roll;
import uk.laxd.dndSimulator.dice.RollResult;
import uk.laxd.dndSimulator.equipment.Weapon;
import uk.laxd.dndSimulator.event.EncounterEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AttackAction {

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

    public AttackAction(Character performer, Weapon weapon, Character target) {
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
}
