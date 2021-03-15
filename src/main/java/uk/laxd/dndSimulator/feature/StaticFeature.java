package uk.laxd.dndSimulator.feature;

/**
 *  A {@code Feature} that is always active, and applies whenever a condition is met, i.e. Brutal Critical
 */
public class StaticFeature extends Feature {
    public StaticFeature(String name) {
        super(name);
    }

    @Override
    public boolean isActiveSkill() {
        return false;
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
