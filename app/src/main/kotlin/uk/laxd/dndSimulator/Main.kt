package uk.laxd.dndSimulator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.CommandLineRunner
import uk.laxd.dndSimulator.action.Simulation
import org.springframework.boot.SpringApplication
import uk.laxd.dndSimulator.config.ConfigParserFactory
import uk.laxd.dndSimulator.equipment.*
import uk.laxd.dndSimulator.event.EventOutputFactory
import uk.laxd.dndSimulator.feature.FeatureRegistry
import uk.laxd.dndSimulator.feature.barbarian.Rage
import uk.laxd.dndSimulator.feature.barbarian.RecklessAttack
import uk.laxd.dndSimulator.feature.barbarian.UnarmoredDefence
import uk.laxd.dndSimulator.feature.generic.ExtraAttack
import uk.laxd.dndSimulator.feature.rogue.SneakAttack

fun main(args: Array<String>) {
    SpringApplication.run(Main::class.java, *args)
}

@SpringBootApplication
open class Main(
    private val eventOutputFactory: EventOutputFactory,
    private val configParserFactory: ConfigParserFactory,
    private val simulation: Simulation,
    private val registry: FeatureRegistry,
    private val armourRegistry: ArmourRegistry
    ) : CommandLineRunner {

    override fun run(vararg args: String) {
        // Find and register all features
        // TODO: Put this somewhere else? Register via reflection?
        registry.register(Rage::class)
        registry.register(RecklessAttack::class)
        registry.register(UnarmoredDefence::class)
        registry.register(SneakAttack::class)
        registry.register(ExtraAttack::class)

        armourRegistry.register(PaddedArmour::class)
        armourRegistry.register(LeatherArmour::class)
        armourRegistry.register(StuddedLeatherArmour::class)
        armourRegistry.register(HideArmour::class)
        armourRegistry.register(ChainShirtArmour::class)
        armourRegistry.register(ScaleMailArmour::class)
        armourRegistry.register(BreastplateArmour::class)
        armourRegistry.register(HalfPlateArmour::class)
        armourRegistry.register(RingMailArmour::class)
        armourRegistry.register(ChainMailArmour::class)
        armourRegistry.register(SplintArmour::class)
        armourRegistry.register(PlateArmour::class)

        val encounterConfig = configParserFactory.getConfigParser()
            .getConfig(args[0])

        simulation.runSimulation(encounterConfig)

        eventOutputFactory.getEventOutput()
            .processEvents()
    }
}