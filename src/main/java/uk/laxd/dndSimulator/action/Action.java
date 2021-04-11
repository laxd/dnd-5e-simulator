package uk.laxd.dndSimulator.action;

import uk.laxd.dndSimulator.event.EncounterEventType;

/**
 * An action represents any action that a player (or creature) may take, and
 * includes, but is not limited to:
 * <ol>
 *     <li>An action</li>
 *     <li>A bonus action</li>
 *     <li>Movement</li>
 *     <li>A free action</li>
 *     <li>Reactions</li>
 * </ol>
 */
public interface Action {

    void performAction();

    EncounterEventType getEventType();

}
