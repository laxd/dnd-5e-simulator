package uk.laxd.dndSimulator.action;

import uk.laxd.dndSimulator.dice.Die;

import java.util.Arrays;

public class DamageModifier {

    private int damageBonus;
    private Die[] diceBonus;

    public DamageModifier(int damageBonus, Die... diceBonus) {
        this.damageBonus = damageBonus;
        this.diceBonus = diceBonus;
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public Die[] getDiceBonus() {
        return diceBonus;
    }

    public int getTotalDamageBonus() {
        return damageBonus + Arrays.stream(diceBonus).mapToInt(Die::roll).sum();
    }
}
