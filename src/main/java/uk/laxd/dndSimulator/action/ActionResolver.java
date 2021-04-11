package uk.laxd.dndSimulator.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.laxd.dndSimulator.event.EncounterEventFactory;
import uk.laxd.dndSimulator.event.EventLogger;

/**
 * <p>
 * Resolves an action, which may involve any of the following:
 * </p>
 *
 * <ol>
 *     <li>An action</li>
 *     <li>A bonus action</li>
 *     <li>Movement</li>
 *     <li>A free action</li>
 * </ol>
 *
 * <p>
 * Resolving an action plays out the intended action, recording the result
 * and resolving all outcomes from that actions (i.e. for attacks, applies
 * damage to the creature being attacked).
 * </p>
 * <p>
 * Reactions as a result of the action resolving (i.e. Opportunity attacks, etc)
 * are resolved as part of this class, and will log their own events etc.
 * </p>
 */
@Component
public class ActionResolver {

    private final EventLogger eventLogger;
    private final EncounterEventFactory eventFactory;

    @Autowired
    public ActionResolver(EventLogger eventLogger, EncounterEventFactory eventFactory) {
        this.eventLogger = eventLogger;
        this.eventFactory = eventFactory;
    }

    public void resolve(Action action) {
        action.performAction();

        eventLogger.logEvent(eventFactory.createNewActionEvent(action));

        // TODO: Reactions
    }

}
