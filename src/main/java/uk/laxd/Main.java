package uk.laxd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.laxd.dndSimulator.action.*;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.ability.Ability;
import uk.laxd.dndSimulator.ability.AbilityCheck;
import uk.laxd.dndSimulator.ability.AbilityCheckOutcome;
import uk.laxd.dndSimulator.character.CharacterBuilder;
import uk.laxd.dndSimulator.equipment.Greatsword;
import uk.laxd.dndSimulator.equipment.UnarmedAttack;
import uk.laxd.dndSimulator.equipment.Weapon;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    // TODO: Load characters from file and run scenarios that way
    public static void main(String[] args) {
        Character character = CharacterBuilder.barbarian(3,"Magnus")
                .withAbilityScores(19, 14, 16, 6, 13, 11)
                .withWeapon(new Greatsword())
                .build();

        Character target = CharacterBuilder.newCharacter(5,"Target")
                .withArmorClass(10)
                .withHp(30)
                .withWeapon(null)
                .build();

        AbilityCheck abilityCheck = new AbilityCheck(Ability.STRENGTH, 15, character);

        AbilityCheckOutcome outcome = abilityCheck.perform();

        LOGGER.info("{} did a {} check: {}", character.getName(), abilityCheck.getType(), outcome);

        Encounter encounter = new Encounter(character, target);

        Simulation simulation = new Simulation();

        simulation.runSimulation(encounter, 10000);

    }
}
