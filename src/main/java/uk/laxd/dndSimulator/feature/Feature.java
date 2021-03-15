package uk.laxd.dndSimulator.feature;

import uk.laxd.dndSimulator.Effect;
import uk.laxd.dndSimulator.action.AttackAction;
import uk.laxd.dndSimulator.character.Character;

/**
 * A {@code Feature} is a specific type of {@link Effect} that
 * a character obtains through their class, race or
 */
public abstract class Feature extends Effect {

    private boolean isActive;

    public Feature(String name) {
        super(name);
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public int getLevelRequirement() {
        return 1;
    }

    public abstract boolean isActiveSkill();

    public boolean isActive() {
        return isActive;
    }

    // TODO: onInitiativeRoll
    // TODO: onTurnStart/onTurnEnd for other characters, i.e. flaming orb, if an enemy ends their turn, damage them

    public void reset() {}
}
