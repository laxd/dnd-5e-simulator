package uk.laxd.dndSimulator.ability;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.dice.Die;
import uk.laxd.dndSimulator.dice.RollModifier;

public class AbilityCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbilityCheck.class);

    private Ability type;
    private int difficultyCheck;
    private Character performer;
    private RollModifier modifier;

    public AbilityCheck(Ability type, int difficultyCheck, Character performer) {
        this(type, difficultyCheck, performer, null);
    }

    public AbilityCheck(Ability type, int difficultyCheck, Character performer, RollModifier modifier) {
        this.type = type;
        this.difficultyCheck = difficultyCheck;
        this.performer = performer;
        this.modifier = modifier;
    }

    public AbilityCheckOutcome perform() {
        LOGGER.debug("Performing ability check: {} - DC: {}", type, difficultyCheck);
        int modifier = performer.getAbilityModifier(type);

        int d20Roll = Die.D20.roll();

        if(d20Roll + modifier >= difficultyCheck) {
            LOGGER.info("{} check passed: {} + {} >= DC of {}", type, d20Roll, modifier, difficultyCheck);
            return AbilityCheckOutcome.PASS;
        }
        else {
            LOGGER.info("{} check failed: {} + {} < DC of {}", type, d20Roll, modifier, difficultyCheck);

            return AbilityCheckOutcome.FAIL;
        }
    }

    public Ability getType() {
        return type;
    }

    public int getDifficultyCheck() {
        return difficultyCheck;
    }

    public Character getPerformer() {
        return performer;
    }
}
