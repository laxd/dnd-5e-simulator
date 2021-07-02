package uk.laxd.dndSimulator.feature

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.config.CharacterConfig
import uk.laxd.dndSimulator.event.EventLogger
import uk.laxd.dndSimulator.feature.barbarian.Rage
import uk.laxd.dndSimulator.feature.barbarian.RecklessAttack
import uk.laxd.dndSimulator.feature.barbarian.UnarmoredDefence
import uk.laxd.dndSimulator.feature.rogue.SneakAttack
import java.util.*
import java.util.stream.Collectors
import kotlin.reflect.KClass

@Component
class FeatureFactory(
    private val registry: FeatureRegistry
) {

    fun createFeatures(config: CharacterConfig): List<Feature> {
        // TODO: Also populate manually added features via CharacterConfig
        // Add CharacterConfig.preventDefaultFeatures? Or just add a "Class" that doesn't have any features?
        return registry.features.stream()
                .map { f -> f.constructors.first().call() }
                .filter { f: Feature -> config.characterClasses.contains(f.characterClassRequired) }
                .filter { f: Feature -> f.levelRequirement <= config.getLevel(f.characterClassRequired) }
                .collect(Collectors.toList())
    }

}