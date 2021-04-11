package uk.laxd.dndSimulator.character;

import uk.laxd.dndSimulator.action.Damage;
import uk.laxd.dndSimulator.dice.Die;
import uk.laxd.dndSimulator.equipment.UnarmedAttack;
import uk.laxd.dndSimulator.equipment.Weapon;
import uk.laxd.dndSimulator.feature.Feature;
import uk.laxd.dndSimulator.ability.Ability;

import java.util.*;
import java.util.stream.Collectors;

public class Character {

    private String name;
    private int hp;
    private int maxHp;
    private Map<CharacterClass, Integer> characterClassLevels = new HashMap<>();
    private Map<Ability, Integer> abilities = new HashMap<>();
    private int attackCount = 1;
    private int armorClass = 10;
    private Collection<Feature> features = new ArrayList<>();

    // TODO: Add support for TWF
    private Weapon weapon = new UnarmedAttack();

    public Character(String name, CharacterClass characterClass, int level) {
        this.characterClassLevels.put(characterClass, level);
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

    public void addLevel(CharacterClass characterClass, int level) {
        this.characterClassLevels.compute(characterClass, (k, v) -> v == null ? level : level + v);
    }

    public int getLevel(CharacterClass characterClass) {
        return this.characterClassLevels.get(characterClass);
    }

    public int getLevel() {
        return this.characterClassLevels.values()
                .stream().mapToInt(i -> i)
                .sum();
    }

    public void setAbilityScore(Ability ability, int score) {
        this.abilities.put(ability, score);
    }

    public int getAbilityScore(Ability ability) {
        return abilities.get(ability);
    }

    public int getAbilityModifier(Ability ability) {
        return (int) Math.floor((abilities.get(ability) - 10)/2.0);
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
        return (int) (1 + Math.ceil(getLevel()/4.0));
    }

    public int getInitiativeModifier() {
        return getAbilityModifier(Ability.DEXTERITY);
    }

    public void applyDamage(Damage damage) {
        // TODO: Apply vulnerabilities/resistances/different types of damage
        int sumDamage = damage.getDamageMap().values().stream().mapToInt(e -> e).sum();
        int totalDamage = (Math.min(sumDamage, this.hp));

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

    public Collection<CharacterClass> getCharacterClasses() {
        return characterClassLevels.keySet();
    }

    public Collection<Die> getHitDice() {
        return this.characterClassLevels.entrySet().stream()
                .flatMap(e -> Collections.nCopies(e.getValue(), e.getKey().getHitDie()).stream())
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Character)) return false;
        Character character = (Character) o;
        return Objects.equals(name, character.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%s (AC=%s, hp=%s/%s)", name, armorClass, hp, maxHp);
    }
}
