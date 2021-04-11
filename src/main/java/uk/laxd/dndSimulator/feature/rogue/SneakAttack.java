package uk.laxd.dndSimulator.feature.rogue;

import uk.laxd.dndSimulator.action.MeleeAttackAction;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.character.CharacterClass;
import uk.laxd.dndSimulator.dice.Die;
import uk.laxd.dndSimulator.dice.Roll;
import uk.laxd.dndSimulator.equipment.WeaponProperty;
import uk.laxd.dndSimulator.feature.StaticFeature;

public class SneakAttack extends StaticFeature {
    private boolean hasSneakAttacked = false;
    private int diceCount = 0;

    public SneakAttack() {
        super("Sneak Attack");
    }

    @Override
    public void onCreate(Character character) {
        diceCount = new Double(Math.ceil(character.getLevel()/2)).intValue();
    }

    @Override
    public void onDamageRoll(MeleeAttackAction action) {
        if(!(action.getWeapon().hasProperty(WeaponProperty.FINESSE) || action.getWeapon().hasProperty(WeaponProperty.RANGE))) {
            // Sneak attack only applies to finesse/ranged attacks
            return;
        }

        if(this.hasSneakAttacked) {
            // 1 sneak attack per turn
            return;
        }

        if(!action.isWithAdvantage()) {
            // Sneak attack only applies if you have adv
            // TODO: Or an ally within 5 ft of target
            return;
        }

        Roll sneakAttackRoll = new Roll();

        for(int i=0; i<diceCount; i++) {
            sneakAttackRoll.addDie(Die.D6);
        }

        action.addDamageRolls(sneakAttackRoll);

        hasSneakAttacked = true;
    }

    @Override
    public void onTurnEnd(Character character) {
        this.hasSneakAttacked = false;
    }

    @Override
    public CharacterClass getCharacterClassRequired() {
        return CharacterClass.ROGUE;
    }
}
