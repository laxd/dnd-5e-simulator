package uk.laxd.dndSimulator.character

import uk.laxd.dndSimulator.dice.Die

// TODO: Although this works for the defined set of classes,
// it makes it difficult for custom homebrew classes (or dnd for other versions?) to be added by
// users implementing this library.
enum class CharacterClass(val hitDie: Die) {
    BARBARIAN(Die.D12),
    FIGHTER(Die.D10),
    PALADIN(Die.D10),
    RANGER(Die.D10),
    ROGUE(Die.D8),
    ARTIFICER(Die.D8),
    BARD(Die.D8),
    CLERIC(Die.D8),
    DRUID(Die.D8),
    MONK(Die.D8),
    WARLOCK(Die.D8),
    SORCERER(Die.D6),
    WIZARD(Die.D6),
    GENERIC_CHARACTER(Die.D6);
}