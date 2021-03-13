package uk.laxd.dndSimulator.feature.generic;

import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.feature.Feature;
import uk.laxd.dndSimulator.feature.StaticFeature;

public class ExtraAttack extends StaticFeature {

    public ExtraAttack() {
        super("Extra Attack");
    }

    @Override
    public void onCreate(Character character) {
        character.setAttackCount(2);
    }
}
