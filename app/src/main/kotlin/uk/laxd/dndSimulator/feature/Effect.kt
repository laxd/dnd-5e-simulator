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

    /**
     * Called when the character is first created. Use to set things that
     * permanently alter the character's stats
     */
    open fun onCreate(character: Character) {}

    /**
     * Called once when combat starts
     */
    open fun onCombatStart(character: Character) {}

    /**
     * Called at the start of combat (after [onCombatStart]) to allow
     * features to modify initiative rolls
     */
    open fun onInitiativeRoll(initiativeRoll: InitiativeRoll) {}

    /**
     * Called every time a new round starts, i.e. once everyone has had a turn
     * and the top of the initiative is started again
     */
    open fun onRoundStart(character: Character) {}

    /**
     * Called once at the start of a [Character]'s turn
     */
    open fun onTurnStart(character: Character) {}

    /**
     * Called once at the end of a [Character]'s turn
     */
    open fun onTurnEnd(character: Character) {}

    /**
     * Called when the [Character] makes an attack roll against
     * another Character, but before the result is decided.
     *
     * Use this method to modify the attack roll i.e. if a feature
     * gives advantage/disadvantage to an attack roll, it should do it here
     */
    open fun onAttackRoll(action: AttackAction) {}

    /**
     * Counterpart to [onAttackRoll]
     *
     * Called when a [Character] has an attack roll made against them,
     * but before the result is decided.
     *
     * Use this method to modify the attack roll i.e. if a feature
     * gives advantage/disadvantage to an attack roll, it should do it here
     */
    open fun onAttackRollReceiving(action: AttackAction) {}

    /**
     * Called when a [Character] hits another with an attack,
     * but before damage has been calculated and applied
     *
     * Use this method to modify the damage roll, i.e. features
     * providing resistance/vulnerability should modify the damage here
     */
    open fun onDamageRoll(action: AttackAction) {}

    /**
     * Counterpart to [onDamageRoll].
     *
     * Called when a [Character] is hit by another with an attack,
     * but before damage has been calculated and applied
     *
     * Use this method to modify the damage roll, i.e. features
     * providing resistance/vulnerability should modify the damage here
     */
    open fun onDamageRollReceived(action: AttackAction) {}

    /**
     * Called when a [Character] has caused damage to another Character (or themselves).
     *
     * This method is called after the damage has been applied.
     */
    open fun onDamageInflicted(action: AttackAction) {}

    /**
     * Called when a [Character] receives damage from any source.
     *
     * This method is called after the damage has been applied.
     */
    open fun onDamageReceived(action: AttackAction) {}

    /**
     * Called when a [Character] makes a saving throw.
     *
     * This method is called before the result of the saving throw takes effect
     */
    open fun onSavingThrow(action: AttackAction) {}

    /**
     * Called when a [Character] makes an ability check.
     *
     * This method is called before the result of the ability check happens.
     */
    open fun onAbilityCheck(character: Character) {}

    /**
     * Placeholder - Called when a [Character] takes a short rest
     */
    open fun onShortRest(character: Character) {}

    /**
     * Placeholder - Called when a [Character] takes a long rest
     */
    open fun onLongRest(character: Character) {}

    /**
     * Called when a [Character] gets a killing blow on another Character
     */
    open fun onKill(killed: Character, killedBy: Character) {}
    override fun toString() = name

}