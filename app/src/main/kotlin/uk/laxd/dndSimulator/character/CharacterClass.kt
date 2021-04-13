package uk.laxd.dndSimulator.character

import uk.laxd.dndSimulator.dice.Die

// TODO: Although this works for the defined set of classes,
// it makes it difficult for custom homebrew classes to be added by
// users implementing this library.
enum class CharacterClass(val hitDie: Die) {
    BARBARIAN(Die.D12), ROGUE(Die.D8), GENERIC_CHARACTER(Die.D6);
}