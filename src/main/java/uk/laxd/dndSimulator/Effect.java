package uk.laxd.dndSimulator;

import uk.laxd.dndSimulator.action.MeleeAttackAction;
import uk.laxd.dndSimulator.action.InitiativeRoll;
import uk.laxd.dndSimulator.character.Character;

/**
 * An {@code Effect} is any change to a character that impacts game play. This includes for example:
 *
 * * Weapons and other equipment
 * * Class/race/other skills
 * * Feats
 * * Conditions
 *
 */
public class Effect {

    private String name;

    public Effect(String name) {
        this.name = name;
    }

    public void onCreate(Character character) {}

    public void onCombatStart(Character character) {}

    public void onInitiativeRoll(InitiativeRoll initiativeRoll) {}

    public void onRoundStart(Character character) {}

    public void onTurnStart(Character character) {}

    public void onTurnEnd(Character character) {}

    public void onAttackRoll(MeleeAttackAction action) {}

    public void onDamageRoll(MeleeAttackAction action) {}

    public void onAttackRollReceiving(MeleeAttackAction action) {}

    public void onDamageRollReceived(MeleeAttackAction action) {}

    public void onDamageInflicted(MeleeAttackAction action) {}

    public void onDamageReceived(MeleeAttackAction action) {}

    public void onSavingThrow(MeleeAttackAction action) {}

    public void onAbilityCheck(Character character) {}

    public void onShortRest(Character character){}

    public void onLongRest(Character character){}

    public void onDeath(Character character){}
}
