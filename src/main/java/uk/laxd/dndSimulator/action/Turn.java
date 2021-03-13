package uk.laxd.dndSimulator.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.laxd.dndSimulator.character.Character;

import java.util.Map;

public class Turn {

    private static final Logger LOGGER = LoggerFactory.getLogger(Turn.class);

    private ActionResolver actionResolver = new ActionResolver();
    private DamageResolver damageResolver = new DamageResolver();

    private Character character;
    private Encounter encounter;

    private int initiative;

    public Turn(Character character, Encounter encounter, int initiative) {
        this.character = character;
        this.encounter = encounter;
        this.initiative = initiative;
    }

    public TurnOutcome doTurn() {
        Character target = encounter.getTarget(character);

        if(target == null) {
            LOGGER.info("Nothing to attack");
            return new TurnOutcome();
        }

        AttackAction attackAction = new AttackAction(character, character.getWeapon(), target);

        actionResolver.resolve(attackAction);
        damageResolver.resolve(attackAction);

        LOGGER.debug(attackAction.toString());

        TurnOutcome outcome = new TurnOutcome();
        outcome.setHit(attackAction.getOutcome() == AttackOutcome.HIT || attackAction.getOutcome() == AttackOutcome.CRIT);
        outcome.setDamage(attackAction.getAttackDamage().getDamageMap().values().stream().mapToInt(e -> e).sum());
        outcome.setTarget(attackAction.getTarget());

        return outcome;
    }

    public Character getCharacter() {
        return character;
    }

    public int getInitiative() {
        return initiative;
    }
}
