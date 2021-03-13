package uk.laxd.dndSimulator.character;

import uk.laxd.dndSimulator.dice.Die;

public class TargetDummy extends Character {
    public TargetDummy(int level, String name) {
        super(level, name);
    }

    @Override
    public Die getHitDie() {
        return Die.D20;
    }
}
