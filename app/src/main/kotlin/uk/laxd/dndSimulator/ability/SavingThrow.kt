package uk.laxd.dndSimulator.ability

import uk.laxd.dndSimulator.character.Character

/**
 * A saving throw represents a type of ability check that a
 * [Character] may have to make in response to an attack, spell cast,
 * or equipment they are carrying
 */
class SavingThrow(
    type: Ability,
    difficultyCheck: Int,
    performer: Character
): AbilityCheck(type, difficultyCheck, performer) {

}