package uk.laxd.dndSimulator.feature.barbarian

import uk.laxd.dndSimulator.feature.ActivatedFeature
import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.action.ActionType
import uk.laxd.dndSimulator.action.AttackAction
import uk.laxd.dndSimulator.character.Character

class RecklessAttack : ActivatedFeature("Reckless Attack") {
    override fun onAttackRoll(action: AttackAction) {
        // Attack recklessly every time
//        reckless = true;
        // Add advantage
        if (isActive) {
            action.withAdvantage()
        }
    }

    override fun onAttackRollReceiving(action: AttackAction) {
        // Add advantage
        if (isActive) {
            action.withAdvantage()
        }
    }

    override fun onTurnStart(character: Character) {
        // Reset state at the start of every turn.
        deactivate()
    }

    override val levelRequirement: Int
        get() = 2

    override val characterClassRequired: CharacterClass
        get() = CharacterClass.BARBARIAN

    override fun reset() {
        super.deactivate()
    }

    override val actionType: ActionType
        get() = ActionType.FREE_ACTION
}