package uk.laxd.dndSimulator.character;

import uk.laxd.dndSimulator.dice.Die;

// TODO: Although this works for the defined set of classes,
// it makes it difficult for custom homebrew classes to be added by
// users implementing this library.
public enum CharacterClass {
    BARBARIAN(Die.D12),
    ROGUE(Die.D8),
    GENERIC_CHARACTER(Die.D6);

    private Die hitDie;

    CharacterClass(Die hitDie) {
        this.hitDie = hitDie;
    }

    public Die getHitDie() {
        return hitDie;
    }
}
