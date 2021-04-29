package uk.laxd.dndSimulator.feature

import uk.laxd.dndSimulator.action.AttackAction
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
    open fun onAttackRoll(action: AttackAction) {}
    open fun onDamageRoll(action: AttackAction) {}
    open fun onAttackRollReceiving(action: AttackAction) {}
    open fun onDamageRollReceived(action: AttackAction) {}
    open fun onDamageInflicted(action: AttackAction) {}
    open fun onDamageReceived(action: AttackAction) {}
    open fun onSavingThrow(action: AttackAction) {}
    open fun onAbilityCheck(character: Character) {}
    open fun onShortRest(character: Character) {}
    open fun onLongRest(character: Character) {}
    open fun onDeath(character: Character) {}
}