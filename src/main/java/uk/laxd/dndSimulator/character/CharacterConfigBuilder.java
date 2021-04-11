package uk.laxd.dndSimulator.character;

import uk.laxd.dndSimulator.ability.Ability;
import uk.laxd.dndSimulator.equipment.Weapon;
import uk.laxd.dndSimulator.feature.barbarian.Rage;
import uk.laxd.dndSimulator.feature.barbarian.RecklessAttack;
import uk.laxd.dndSimulator.feature.barbarian.UnarmoredDefence;

public class CharacterConfigBuilder {

    private CharacterConfig characterConfig;

    private CharacterConfigBuilder(){}

    public static CharacterConfigBuilder newCharacter(String name) {
        CharacterConfigBuilder builder = new CharacterConfigBuilder();
        builder.characterConfig = new CharacterConfig();
        builder.characterConfig.setName(name);

        // Set all abilities to 10 by default
        builder.characterConfig.setAbilityScore(Ability.STRENGTH, 10);
        builder.characterConfig.setAbilityScore(Ability.DEXTERITY, 10);
        builder.characterConfig.setAbilityScore(Ability.CONSTITUTION, 10);
        builder.characterConfig.setAbilityScore(Ability.INTELLIGENCE, 10);
        builder.characterConfig.setAbilityScore(Ability.WISDOM, 10);
        builder.characterConfig.setAbilityScore(Ability.CHARISMA, 10);

        return builder;
    }

    public CharacterConfigBuilder withLevels(int level, CharacterClass characterClass) {
        this.characterConfig.addLevel(level, characterClass);
        return this;
    }

    public CharacterConfigBuilder withArmourClass(short ac) {
        characterConfig.setArmourClass(ac);
        return this;
    }

    public CharacterConfigBuilder withAbilityScores(int str, int dex, int con, int intelligence, int wis, int cha) {
        characterConfig.setAbilityScore(Ability.STRENGTH, str);
        characterConfig.setAbilityScore(Ability.DEXTERITY, dex);
        characterConfig.setAbilityScore(Ability.CONSTITUTION, con);
        characterConfig.setAbilityScore(Ability.INTELLIGENCE, intelligence);
        characterConfig.setAbilityScore(Ability.WISDOM, wis);
        characterConfig.setAbilityScore(Ability.CHARISMA, cha);

        // Set default HP

        return this;
    }

    public CharacterConfigBuilder withAbilityScore(Ability ability, int score) {
        characterConfig.setAbilityScore(ability, score);
        return this;
    }

    public CharacterConfigBuilder withHp(short hp) {
        characterConfig.setHp(hp);
        return this;
    }

    // TODO: Change to weapon lookups by ID/Name? Custom weapon definitions?
    public CharacterConfigBuilder withWeapon(Weapon weapon) {
        //characterConfig.setWeapon(weapon);
        return this;
    }

    public CharacterConfig build() {
        return characterConfig;
    }

    // TODO: Move to Character creation
//    public Character build() {
//        // Set default HP if not already set
//        if(character.getMaxHp() == 0) {
//            int hp = character.getHitDie().getMaxValue()
//                    + (character.getLevel() * character.getAbilityModifier(Ability.CONSTITUTION))
//                    + ((character.getLevel() - 1) * ((character.getHitDie().getMaxValue()/2)+1));
//
//            character.setMaxHp(hp);
//        }
//
//        character.setHp(character.getMaxHp());
//
//        // Tell all features they have been set
//        character.getFeatures()
//                .forEach(f -> f.onCreate(character));
//
//        return character;
//    }

}
