package uk.laxd.dndSimulator.action;

import uk.laxd.dndSimulator.character.Character;

public class TurnOutcome {
    private boolean hit = false;
    private int damage = 0;
    private Character target = null;

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Character getTarget() {
        return target;
    }

    public void setTarget(Character target) {
        this.target = target;
    }
}
