package uk.laxd.dndSimulator.feature;

import uk.laxd.dndSimulator.action.ActionType;

/**
 * A feature that needs to be actively used in order to be active, i.e. a Barbarian's Rage.
 */
public abstract class ActivatedFeature extends Feature {
    public ActivatedFeature(String name) {
        super(name);
    }

    @Override
    public boolean isActiveSkill() {
        return true;
    }

    public abstract ActionType getActionType();

    /**
     * Priority represents how important it is that this is applied.
     * @return
     */

}
