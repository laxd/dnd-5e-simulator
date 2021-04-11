package uk.laxd.dndSimulator.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.laxd.dndSimulator.ability.Ability;
import uk.laxd.dndSimulator.feature.FeatureFactory;

@Component
public class CharacterFactory {

    private final FeatureFactory featureFactory;

    @Autowired
    public CharacterFactory(FeatureFactory featureFactory) {
        this.featureFactory = featureFactory;
    }

    public Character createCharacter(CharacterConfig characterConfig) {
        Character character = new Character(characterConfig.getName(),
                CharacterClass.BARBARIAN,
                characterConfig.getLevel(CharacterClass.BARBARIAN));

        characterConfig.getAbilityScores().forEach(character::setAbilityScore);

        short hp = getHp(character, characterConfig);

        character.setMaxHp(hp);
        character.setHp(hp);
        character.setArmorClass(characterConfig.getArmourClass());

        featureFactory.createFeatures(characterConfig)
                .forEach(character::addFeature);

        return character;
    }

    private short getHp(Character character, CharacterConfig characterConfig) {
        // If HP has been set, use that
        if(character.getHp() != 0) {
            return characterConfig.getHp();
        }

        // If not, calculate HP from stats, and set it
        short hp = (short) (character.getLevel() * character.getAbilityModifier(Ability.CONSTITUTION));

        hp += character.getHitDice().stream()
                .mapToInt(d -> (d.getMaxValue()/2) + 1)
                .sum();

        return hp;
    }

}
