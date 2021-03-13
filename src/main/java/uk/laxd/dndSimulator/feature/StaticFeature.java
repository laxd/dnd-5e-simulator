package uk.laxd.dndSimulator.feature;

/* A feature that is always active, and doesn't need to be activated manually, i.e. Brutal Critical */
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
