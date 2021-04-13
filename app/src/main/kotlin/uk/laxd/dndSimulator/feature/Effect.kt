package uk.laxd.dndSimulator.feature

import uk.laxd.dndSimulator.action.InitiativeRoll
import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.character.Character

/**
 * An `Effect` is any change to a character that impacts game play. This includes for example:
 *
 * * Weapons and other equipment
 * * Class/race/other skills
 * * Feats
 * * Conditions
 *
 */
open class Effect(val name: String) {
    open fun onCreate(character: Character) {}
    open fun onCombatStart(character: Character) {}
    open fun onInitiativeRoll(initiativeRoll: InitiativeRoll) {}
    open fun onRoundStart(character: Character) {}
    open fun onTurnStart(character: Character) {}
    open fun onTurnEnd(character: Character) {}
    open fun onAttackRoll(action: MeleeAttackAction) {}
    open fun onDamageRoll(action: MeleeAttackAction) {}
    open fun onAttackRollReceiving(action: MeleeAttackAction) {}
    open fun onDamageRollReceived(action: MeleeAttackAction) {}
    open fun onDamageInflicted(action: MeleeAttackAction) {}
    open fun onDamageReceived(action: MeleeAttackAction) {}
    open fun onSavingThrow(action: MeleeAttackAction) {}
    open fun onAbilityCheck(character: Character) {}
    open fun onShortRest(character: Character) {}
    open fun onLongRest(character: Character) {}
    open fun onDeath(character: Character) {}
}