package uk.laxd.dndSimulator.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.event.EncounterEventFactory;
import uk.laxd.dndSimulator.event.EventLogger;

@Component
public class TurnFactory {

    private final EncounterEventFactory eventFactory;
    private final EventLogger eventLogger;
    private final ActionResolver actionResolver;
    private final DamageResolver damageResolver;

    @Autowired
    public TurnFactory(EncounterEventFactory eventFactory, EventLogger eventLogger, ActionResolver actionResolver, DamageResolver damageResolver) {
        this.eventFactory = eventFactory;
        this.eventLogger = eventLogger;
        this.actionResolver = actionResolver;
        this.damageResolver = damageResolver;
    }

    public Turn createTurn(Character character, TargetSelector targetSelector) {
        return new Turn(eventFactory, eventLogger, actionResolver, damageResolver, character, targetSelector);
    }

}
