package uk.laxd.dndSimulator.feature.barbarian;

import uk.laxd.dndSimulator.ability.Ability;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.character.CharacterClass;
import uk.laxd.dndSimulator.feature.Feature;
import uk.laxd.dndSimulator.feature.StaticFeature;

public class UnarmoredDefence extends StaticFeature {
    public UnarmoredDefence() {
        super("Unarmored Defence");
    }

    @Override
    public void onCreate(Character character) {
        // Set AC if not wearing armour
        // if (!character.getEquippedItems().isWearingArmour())
        int ac = 10 + character.getAbilityModifier(Ability.DEXTERITY) + character.getAbilityModifier(Ability.CONSTITUTION);
        character.setArmorClass(ac);
    }

    @Override
    public CharacterClass getCharacterClassRequired() {
        return CharacterClass.BARBARIAN;
    }
}
