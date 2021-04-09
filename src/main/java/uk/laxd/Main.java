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
import uk.laxd.dndSimulator.event.EncounterEventFactory;
import uk.laxd.dndSimulator.event.EventLogger;
import uk.laxd.dndSimulator.statistics.StatsPrinter;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    // TODO: Load characters from file and run scenarios that way
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    private final StatsPrinter statsPrinter;
    private final TurnFactory turnFactory;
    private final EventLogger eventLogger;
    private final EncounterEventFactory eventFactory;

    @Autowired
    public Main(StatsPrinter statsPrinter, TurnFactory turnFactory, EventLogger eventLogger, EncounterEventFactory eventFactory) {
        this.statsPrinter = statsPrinter;
        this.turnFactory = turnFactory;
        this.eventLogger = eventLogger;
        this.eventFactory = eventFactory;
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

        Encounter encounter = new Encounter(turnFactory, eventFactory, eventLogger, character, target);

        Simulation simulation = new Simulation();

        simulation.runSimulation(encounter, 100);

        statsPrinter.printStats();
    }
}
