package uk.laxd.dndSimulator.character;

import uk.laxd.dndSimulator.ability.Ability;
import uk.laxd.dndSimulator.equipment.Weapon;
import uk.laxd.dndSimulator.feature.barbarian.Rage;
import uk.laxd.dndSimulator.feature.barbarian.RecklessAttack;
import uk.laxd.dndSimulator.feature.barbarian.UnarmoredDefence;

public class CharacterBuilder {

    private Character character;

    private CharacterBuilder(){}

    public static CharacterBuilder newCharacter(int level, String name) {
        CharacterBuilder builder = new CharacterBuilder();
        builder.character = new GenericCharacter(level, name);
        builder.character.setLevel(level);

        // Set all abilities to 10 by default
        builder.character.setAbilityScore(Ability.STRENGTH, 10);
        builder.character.setAbilityScore(Ability.DEXTERITY, 10);
        builder.character.setAbilityScore(Ability.CONSTITUTION, 10);
        builder.character.setAbilityScore(Ability.INTELLIGENCE, 10);
        builder.character.setAbilityScore(Ability.WISDOM, 10);
        builder.character.setAbilityScore(Ability.CHARISMA, 10);

        return builder;
    }

    public static CharacterBuilder barbarian(int level, String name) {
        CharacterBuilder builder = new CharacterBuilder();
        builder.character = new Barbarian(level, name);

        builder.character.addFeature(new UnarmoredDefence());
        builder.character.addFeature(new Rage());
        builder.character.addFeature(new RecklessAttack());

        // Set all abilities to 10 by default
        builder.character.setAbilityScore(Ability.STRENGTH, 10);
        builder.character.setAbilityScore(Ability.DEXTERITY, 10);
        builder.character.setAbilityScore(Ability.CONSTITUTION, 10);
        builder.character.setAbilityScore(Ability.INTELLIGENCE, 10);
        builder.character.setAbilityScore(Ability.WISDOM, 10);
        builder.character.setAbilityScore(Ability.CHARISMA, 10);

        return builder;
    }

    public CharacterBuilder withArmorClass(int ac) {
        character.setArmorClass(ac);
        return this;
    }

    public CharacterBuilder withAbilityScores(int str, int dex, int con, int intelligence, int wis, int cha) {
        character.setAbilityScore(Ability.STRENGTH, str);
        character.setAbilityScore(Ability.DEXTERITY, dex);
        character.setAbilityScore(Ability.CONSTITUTION, con);
        character.setAbilityScore(Ability.INTELLIGENCE, intelligence);
        character.setAbilityScore(Ability.WISDOM, wis);
        character.setAbilityScore(Ability.CHARISMA, cha);

        // Set default HP

        return this;
    }

    public CharacterBuilder withAbilityScore(Ability ability, int score) {
        character.setAbilityScore(ability, score);
        return this;
    }

    public CharacterBuilder withHp(int hp) {
        character.setMaxHp(hp);
        return this;
    }

    public CharacterBuilder withWeapon(Weapon weapon) {
        character.setWeapon(weapon);
        return this;
    }

    public Character build() {
        // Set default HP if not already set
        if(character.getMaxHp() == 0) {
            int hp = character.getHitDie().getMaxValue()
                    + (character.getLevel() * character.getAbilityModifier(Ability.CONSTITUTION))
                    + ((character.getLevel() - 1) * ((character.getHitDie().getMaxValue()/2)+1));

            character.setMaxHp(hp);
        }

        character.setHp(character.getMaxHp());

        // Tell all features they have been set
        character.getFeatures()
                .forEach(f -> f.onCreate(character));

        return character;
    }

}
