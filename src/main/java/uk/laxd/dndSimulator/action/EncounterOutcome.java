package uk.laxd.dndSimulator.action;

import uk.laxd.dndSimulator.character.Character;

public class EncounterOutcome {

    private Character character;
    private int remainingHp;
    private int turnsTaken = 0;
    private int timesAttacked = 0;
    private int timesHit = 0;
    private int totalDamageInflicted = 0;
    private int totalDamageTaken = 0;
    private int totalDamageHealed = 0;

    public EncounterOutcome(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public int getRemainingHp() {
        return remainingHp;
    }

    public void setRemainingHp(int remainingHp) {
        this.remainingHp = remainingHp;
    }

    public int getTurnsTaken() {
        return turnsTaken;
    }

    public void incrementTurnsTaken() {
        this.turnsTaken++;
    }

    public int getTimesAttacked() {
        return timesAttacked;
    }

    public void incrementTimesAttacked() {
        this.timesAttacked++;
    }

    public int getTimesHit() {
        return timesHit;
    }

    public void incrementTimesHit() {
        this.timesHit++;
    }

    public int getTotalDamageInflicted() {
        return totalDamageInflicted;
    }

    public void incrementTotalDamageInflicted(int totalDamageInflicted) {
        this.totalDamageInflicted += totalDamageInflicted;
    }

    public int getTotalDamageTaken() {
        return totalDamageTaken;
    }

    public void incrementTotalDamageTaken(int totalDamageTaken) {
        this.totalDamageTaken += totalDamageTaken;
    }

    public int getTotalDamageHealed() {
        return totalDamageHealed;
    }

    public void incrementTotalDamageHealed(int totalDamageHealed) {
        this.totalDamageHealed += totalDamageHealed;
    }
}
