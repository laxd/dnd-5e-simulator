package uk.laxd.dndSimulator.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.event.EncounterEventFactory;
import uk.laxd.dndSimulator.event.EventLogger;

public class Turn {

    private static final Logger LOGGER = LoggerFactory.getLogger(Turn.class);

    private final EncounterEventFactory eventFactory;
    private final EventLogger eventLogger;
    private final ActionResolver actionResolver;
    private final DamageResolver damageResolver;
    private final TargetSelector targetSelector;

    private Character character;

    public Turn(EncounterEventFactory eventFactory, EventLogger eventLogger, ActionResolver actionResolver, DamageResolver damageResolver, Character character, TargetSelector targetSelector) {
        this.eventFactory = eventFactory;
        this.eventLogger = eventLogger;
        this.actionResolver = actionResolver;
        this.damageResolver = damageResolver;
        this.character = character;
        this.targetSelector = targetSelector;
    }

    public void doTurn() {
        eventLogger.logEvent(eventFactory.createTurnStartEvent(this));

        Character target = targetSelector.getTarget();

        if(target == null) {
            LOGGER.info("Nothing to attack");
            return;
        }

        AttackAction attackAction = new AttackAction(character, character.getWeapon(), target);

        actionResolver.resolve(attackAction);
        damageResolver.resolve(attackAction);

        LOGGER.debug(attackAction.toString());
    }

    public Character getCharacter() {
        return character;
    }
}
