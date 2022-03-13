package uk.laxd.dndSimulator.action.initiative

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.action.InitiativeRoll
import uk.laxd.dndSimulator.character.Character

interface InitiativeProvider {
    fun getInitiative(character: Character): Int
}

@Component
class SimpleInitiativeProvider : InitiativeProvider {
    override fun getInitiative(character: Character): Int {
        val roll = InitiativeRoll()

        character.onInitiativeRoll(roll)

        return roll.roll().outcome + character.initiativeModifier
    }

}