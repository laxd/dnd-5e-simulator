package uk.laxd.dndSimulator.dice

/**
 * Represents a modification that needs to be made to a die roll, for example:
 *
 * * Advantage - Roll twice, take highest
 * * Disadvantage - Roll twice, take lowest
 * * Bless - Add a d4
 *
 */
abstract class RollModifier {
    fun modifyDice(roll: Roll) {}
    fun modifyRoll(roll: Roll) {}
}