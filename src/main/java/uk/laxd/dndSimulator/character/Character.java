package uk.laxd.dndSimulator.character;

import uk.laxd.dndSimulator.action.Damage;
import uk.laxd.dndSimulator.dice.Die;
import uk.laxd.dndSimulator.equipment.UnarmedAttack;
import uk.laxd.dndSimulator.equipment.Weapon;
import uk.laxd.dndSimulator.feature.Feature;
import uk.laxd.dndSimulator.ability.Ability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Character {

    private String name;
    private int hp;
    private int maxHp;
    private int level;
    private Map<Ability, Integer> abilities = new HashMap<>();
    private int attackCount = 1;
    private int armorClass = 10;
    private Collection<Feature> features = new ArrayList<>();

    // TODO: Add support for TWF
    private Weapon weapon = new UnarmedAttack();

    public Character(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAbilityScore(Ability ability, int score) {
        this.abilities.put(ability, score);
    }

    public int getAbilityScore(Ability ability) {
        return abilities.get(ability);
    }

    public int getAbilityModifier(Ability ability) {
        return (int) Math.floor((abilities.get(ability) - 10)/2);
    }

    public int getAttackCount() {
        return attackCount;
    }

    public void setAttackCount(int attackCount) {
        this.attackCount = attackCount;
    }

    public Collection<Feature> getFeatures() {
        return new ArrayList<>(features);
    }

    public void addFeature(Feature feature) {
        features.add(feature);
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public int getProficiencyBonus() {
        return (int) (1 + Math.ceil(level/4));
    }

    public int getInitiativeModifier() {
        return getAbilityModifier(Ability.DEXTERITY);
    }

    public void applyDamage(Damage damage) {
        // TODO: Apply vulnerabilities/resistances/different types of damage
        int sumDamage = damage.getDamageMap().values().stream().mapToInt(e -> e).sum();
        int totalDamage = (sumDamage > this.hp ? this.hp : sumDamage);

        this.hp -= totalDamage;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public abstract Die getHitDie();

    public void reset() {
        this.hp = maxHp;
        this.attackCount = 1;
        this.armorClass = 10;

        features.forEach(Feature::reset);
    }

    @Override
    public String toString() {
        return String.format("%s (AC=%s, hp=%s/%s)", name, armorClass, hp, maxHp);
    }
}
