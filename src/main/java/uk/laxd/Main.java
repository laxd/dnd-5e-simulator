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
import uk.laxd.dndSimulator.character.CharacterClass;
import uk.laxd.dndSimulator.character.CharacterConfigBuilder;
import uk.laxd.dndSimulator.character.CharacterConfig;
import uk.laxd.dndSimulator.equipment.Greatsword;
import uk.laxd.dndSimulator.statistics.StatsPrinter;

import java.util.Arrays;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    // TODO: Load characters from file and run scenarios that way
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    private final StatsPrinter statsPrinter;
    private final EncounterFactory encounterFactory;

    @Autowired
    public Main(StatsPrinter statsPrinter, EncounterFactory encounterFactory) {
        this.statsPrinter = statsPrinter;
        this.encounterFactory = encounterFactory;
    }

    @Override
    public void run(String... args) throws Exception {
        CharacterConfig character = CharacterConfigBuilder.newCharacter("Magnus")
                .withLevels(4, CharacterClass.BARBARIAN)
                .withAbilityScores(19, 14, 16, 6, 13, 11)
                .withWeapon(new Greatsword())
                .withHp((short) 50)
                .build();

        CharacterConfig target = CharacterConfigBuilder.newCharacter("Target")
                .withLevels(5, CharacterClass.BARBARIAN)
                .withArmourClass((short) 10)
                .withHp((short) 30)
                .withWeapon(null)
                .build();

        EncounterConfig config = new EncounterConfig(Arrays.asList(character, target));

        Simulation simulation = new Simulation(encounterFactory);
        simulation.runSimulation(config, 10000);

        statsPrinter.printStats();
    }
}
