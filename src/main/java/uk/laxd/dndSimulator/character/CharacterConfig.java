package uk.laxd.dndSimulator.character;

import uk.laxd.dndSimulator.ability.Ability;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines a {@link Character} in a configuration style, allowing for repeatable
 * instantiation of a single character from a set configuration.
 */
public class CharacterConfig {

    // TODO: Allow multi-classing
    private String name;
    private short hp;
    private short armourClass;
    private Map<CharacterClass, Integer> characterClassLevels = new HashMap<>();
    private Map<Ability, Integer> abilityScores = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel(CharacterClass characterClass) {
        return characterClassLevels.get(characterClass);
    }

    public void addLevel(int level, CharacterClass characterClass) {
        characterClassLevels.compute(characterClass, (k, v) -> v == null ? level : v + level);
    }

    public void setAbilityScore(Ability ability, Integer score) {
        abilityScores.put(ability, score);
    }

    public Map<Ability, Integer> getAbilityScores() {
        return abilityScores;
    }

    public short getHp() {
        return hp;
    }

    public void setHp(short hp) {
        this.hp = hp;
    }

    public short getArmourClass() {
        return armourClass;
    }

    public void setArmourClass(short armourClass) {
        this.armourClass = armourClass;
    }

    public Collection<CharacterClass> getCharacterClasses() {
        return characterClassLevels.keySet();
    }
}
