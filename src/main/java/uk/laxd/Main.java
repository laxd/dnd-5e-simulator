package uk.laxd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.laxd.dndSimulator.action.*;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.ability.Ability;
import uk.laxd.dndSimulator.ability.AbilityCheck;
import uk.laxd.dndSimulator.ability.AbilityCheckOutcome;
import uk.laxd.dndSimulator.character.CharacterBuilder;
import uk.laxd.dndSimulator.equipment.Greatsword;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    // TODO: Load characters from file and run scenarios that way
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    private final ActionResolver actionResolver;
    private final DamageResolver damageResolver;

    @Autowired
    public Main(ActionResolver actionResolver, DamageResolver damageResolver) {
        this.actionResolver = actionResolver;
        this.damageResolver = damageResolver;
    }

    @Override
    public void run(String... args) throws Exception {
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

        Encounter encounter = new Encounter(actionResolver, damageResolver, character, target);

        Simulation simulation = new Simulation();

        simulation.runSimulation(encounter, 10000);
    }
}
