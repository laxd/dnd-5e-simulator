package uk.laxd.dndSimulator.feature.barbarian;

import uk.laxd.dndSimulator.action.*;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.dice.Roll;
import uk.laxd.dndSimulator.dice.RollDecorator;
import uk.laxd.dndSimulator.feature.ActivatedFeature;
import uk.laxd.dndSimulator.feature.Feature;

import java.util.Arrays;
import java.util.Collection;

public class Rage extends ActivatedFeature {

    private static final int MAX_RAGE_ROUNDS = 10;

    private int ragesPerDay;
    private int ragesRemaining;
    private int rageDamageBonus;
    private int rageRounds = 0;

    public Rage() {
        super("Rage");
    }

    @Override
    public void onCreate(Character character) {
        // Assume we are ALWAYS raging
        this.activate();

        int level = character.getLevel();

        if(level <= 2) {
            ragesPerDay = 2;
        }
        else if (level <= 5) {
            ragesPerDay = 3;
        }
        else if (level <= 11) {
            ragesPerDay = 4;
        }
        else if (level <= 16) {
            ragesPerDay = 5;
        }
        else if (level <= 19) {
            ragesPerDay = 6;
        }
        else if(level == 20) {
            ragesPerDay = -1;
        }

        ragesRemaining = ragesPerDay;

        if(level <= 8) {
            rageDamageBonus = 2;
        }
        else if(level <= 15) {
            rageDamageBonus = 3;
        }
        else {
            rageDamageBonus = 4;
        }
    }

    @Override
    public void activate() {
        super.activate();

        this.rageRounds = 0;
        this.ragesRemaining--;
    }

    @Override
    public void onTurnStart(Character character) {
        if(isActive()) {
            this.rageRounds++;

            if(this.rageRounds >= MAX_RAGE_ROUNDS) {
                this.deactivate();
            }
        }
    }

    @Override
    public void onDamageRoll(AttackAction action) {
        // TODO: This just increments the damage of the weapon? Need to find the damage roll
        // for just the weapon somehow, or set it separately

        DamageType damageType = action.getWeapon().getDamageType();

        action.getAttackDamage().addAmount(damageType, 2);
    }

    @Override
    public void onDamageRollReceived(AttackAction action) {
        Damage damage = action.getAttackDamage();

        damage.addAmount(DamageType.PIERCING, -damage.getAmount(DamageType.PIERCING)/2);
        damage.addAmount(DamageType.SLASHING, -damage.getAmount(DamageType.SLASHING)/2);
        damage.addAmount(DamageType.BLUDGEONING, -damage.getAmount(DamageType.BLUDGEONING)/2);
    }

    @Override
    public void onAbilityCheck(Character character) {
        if(isActive()) {
            // If strength check, +2
        }
    }

    @Override
    public void onLongRest(Character character) {
        this.ragesRemaining = ragesPerDay;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.BONUS_ACTION;
    }
}
