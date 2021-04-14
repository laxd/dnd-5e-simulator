package uk.laxd.dndSimulator

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import uk.laxd.dndSimulator.statistics.StatsPrinter
import uk.laxd.dndSimulator.action.EncounterFactory
import org.springframework.boot.CommandLineRunner
import kotlin.Throws
import uk.laxd.dndSimulator.character.CharacterConfigBuilder
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.equipment.Greatsword
import uk.laxd.dndSimulator.action.EncounterConfig
import java.util.Arrays
import uk.laxd.dndSimulator.action.Simulation
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication
import uk.laxd.dndSimulator.event.EventOutputFactory
import java.lang.Exception

fun main(args: Array<String>) {
    SpringApplication.run(Main::class.java, *args)
}

@SpringBootApplication
open class Main(
    private val eventOutputFactory: EventOutputFactory,
    private val encounterFactory: EncounterFactory
    ) : CommandLineRunner {

    @Throws(Exception::class)
    override fun run(vararg args: String) {
        // TODO: Load these details from json/csv file/xml/whatever
        val character = CharacterConfigBuilder.newCharacter("Magnus")
                .withLevels(4, CharacterClass.BARBARIAN)
                .withAbilityScores(19, 14, 16, 6, 13, 11)
                .withWeapon(Greatsword())
                .withHp(50.toShort())
                .build()
        val target = CharacterConfigBuilder.newCharacter("Target")
                .withLevels(5, CharacterClass.BARBARIAN)
                .withArmourClass(10.toShort())
                .withHp(30.toShort())
                .build()
        val config = EncounterConfig(listOf(character, target))

        // TODO: SimulationFactory?
        val simulation = Simulation(encounterFactory)
        simulation.runSimulation(config, 10000)

        eventOutputFactory.getEventOutput()
            .processEvents()
    }
}