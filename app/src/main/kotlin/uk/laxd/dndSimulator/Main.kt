package uk.laxd.dndSimulator

import org.springframework.boot.autoconfigure.SpringBootApplication
import uk.laxd.dndSimulator.action.EncounterFactory
import org.springframework.boot.CommandLineRunner
import uk.laxd.dndSimulator.action.Simulation
import org.springframework.boot.SpringApplication
import uk.laxd.dndSimulator.config.ConfigParserFactory
import uk.laxd.dndSimulator.config.json.JsonConfigParser
import uk.laxd.dndSimulator.event.EventOutputFactory

fun main(args: Array<String>) {
    SpringApplication.run(Main::class.java, *args)
}

@SpringBootApplication
open class Main(
    private val eventOutputFactory: EventOutputFactory,
    private val configParserFactory: ConfigParserFactory,
    private val simulation: Simulation
    ) : CommandLineRunner {

    override fun run(vararg args: String) {
        val encounterConfig = configParserFactory.getConfigParser()
            .getConfig("app/src/main/resources/test.json")

        simulation.runSimulation(encounterConfig)

        eventOutputFactory.getEventOutput()
            .processEvents()
    }
}