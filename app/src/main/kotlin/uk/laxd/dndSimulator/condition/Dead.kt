package uk.laxd.dndSimulator.condition

import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.Die

class Dead : Condition("Dead") {

    private var goodRolls = 0
    private var badRolls = 0

    override fun onTurnStart(character: Character) {
        when (Die.D20.roll()) {
            1 -> badRolls += 2
            in 2..9 -> badRolls += 1
            in 10..19 -> goodRolls += 1
            20 -> goodRolls += 2
        }

        if (badRolls >= 3) {
            // Death!
        } else if (goodRolls >= 3) {
            // Stabilise
        }
    }
}