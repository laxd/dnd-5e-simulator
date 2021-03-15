package uk.laxd.dndSimulator.action;

import uk.laxd.dndSimulator.character.Character;

public class SimpleTargetSelector extends TargetSelector {

    private Character target;

    public SimpleTargetSelector(Character target) {
        this.target = target;
    }

    @Override
    public Character getTarget() {
        return target;
    }
}
