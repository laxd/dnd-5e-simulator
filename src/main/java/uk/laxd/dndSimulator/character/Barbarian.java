package uk.laxd.dndSimulator.character;

import uk.laxd.dndSimulator.dice.Die;

public class Barbarian extends Character {
    public Barbarian(int level, String name) {
        super(level, name);
    }

    @Override
    public Die getHitDie() {
        return Die.D12;
    }
}
