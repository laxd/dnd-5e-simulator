package uk.laxd.dndSimulator.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.laxd.dndSimulator.character.Character;

public class Turn {

    private static final Logger LOGGER = LoggerFactory.getLogger(Turn.class);

    private ActionResolver actionResolver;
    private DamageResolver damageResolver;
    private TargetSelector targetSelector;

    private Character character;

    public Turn(ActionResolver actionResolver, DamageResolver damageResolver, Character character, TargetSelector targetSelector) {
        this.actionResolver = actionResolver;
        this.damageResolver = damageResolver;
        this.character = character;
        this.targetSelector = targetSelector;
    }

    public TurnOutcome doTurn() {
        Character target = targetSelector.getTarget();

        if(target == null) {
            LOGGER.info("Nothing to attack");
            return new TurnOutcome();
        }

        AttackAction attackAction = new AttackAction(character, character.getWeapon(), target);

        // TODO: Update to add EncounterEvents - add this to the TurnOutcome so they can be collated in the EncounterOutcome
        actionResolver.resolve(attackAction);
        damageResolver.resolve(attackAction);

        LOGGER.debug(attackAction.toString());

        TurnOutcome outcome = new TurnOutcome();
        outcome.setHit(attackAction.getOutcome() == AttackOutcome.HIT || attackAction.getOutcome() == AttackOutcome.CRIT);
        outcome.setDamage(attackAction.getAttackDamage().getDamageMap().values().stream().mapToInt(e -> e).sum());
        outcome.setTarget(attackAction.getTarget());
        outcome.addEvents(attackAction.getEvents());

        return outcome;
    }

    public Character getCharacter() {
        return character;
    }
}
