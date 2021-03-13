package uk.laxd.dndSimulator.condition;

import uk.laxd.dndSimulator.Effect;
import uk.laxd.dndSimulator.feature.Feature;

public abstract class Condition extends Effect {
    public Condition(String name) {
        super(name);
    }
}
