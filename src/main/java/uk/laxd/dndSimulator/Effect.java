package uk.laxd.dndSimulator;

import uk.laxd.dndSimulator.action.AttackAction;
import uk.laxd.dndSimulator.action.InitiativeRoll;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.dice.Roll;

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

    public void onAttackRoll(AttackAction action) {}

    public void onDamageRoll(AttackAction action) {}

    public void onAttackRollReceiving(AttackAction action) {}

    public void onDamageRollReceived(AttackAction action) {}

    public void onDamageInflicted(AttackAction action) {}

    public void onDamageReceived(AttackAction action) {}

    public void onSavingThrow(AttackAction action) {}

    public void onAbilityCheck(Character character) {}

    public void onShortRest(Character character){}

    public void onLongRest(Character character){}

    public void onDeath(Character character){}
}
