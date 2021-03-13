package uk.laxd.dndSimulator.character;

import uk.laxd.dndSimulator.dice.Die;

public class GenericCharacter extends Character {
    public GenericCharacter(int level, String name) {
        super(level, name);
    }

    @Override
    public Die getHitDie() {
        return Die.D8;
    }
}
