package uk.laxd.dndSimulator

import org.springframework.boot.autoconfigure.SpringBootApplication
import uk.laxd.dndSimulator.action.EncounterFactory
import org.springframework.boot.CommandLineRunner
import uk.laxd.dndSimulator.action.Simulation
import org.springframework.boot.SpringApplication
import uk.laxd.dndSimulator.config.ConfigParserFactory
import uk.laxd.dndSimulator.config.json.JsonConfigParser
import uk.laxd.dndSimulator.event.EventOutputFactory
import uk.laxd.dndSimulator.feature.FeatureRegistry
import uk.laxd.dndSimulator.feature.barbarian.Rage
import uk.laxd.dndSimulator.feature.barbarian.RecklessAttack
import uk.laxd.dndSimulator.feature.barbarian.UnarmoredDefence
import uk.laxd.dndSimulator.feature.rogue.SneakAttack

fun main(args: Array<String>) {
    SpringApplication.run(Main::class.java, *args)
}

@SpringBootApplication
open class Main(
    private val eventOutputFactory: EventOutputFactory,
    private val configParserFactory: ConfigParserFactory,
    private val simulation: Simulation,
    private val registry: FeatureRegistry
    ) : CommandLineRunner {

    override fun run(vararg args: String) {
        // Find and register all features
        // TODO: Put this somewhere else? Register via reflection?
        registry.register(Rage::class)
        registry.register(RecklessAttack::class)
        registry.register(UnarmoredDefence::class)
        registry.register(SneakAttack::class)

        val encounterConfig = configParserFactory.getConfigParser()
            .getConfig(args[0])

        simulation.runSimulation(encounterConfig)

        eventOutputFactory.getEventOutput()
            .processEvents()
    }
}