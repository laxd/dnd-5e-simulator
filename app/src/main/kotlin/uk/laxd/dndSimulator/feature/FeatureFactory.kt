package uk.laxd.dndSimulator.feature

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.config.CharacterConfig
import uk.laxd.dndSimulator.feature.barbarian.Rage
import uk.laxd.dndSimulator.feature.barbarian.RecklessAttack
import uk.laxd.dndSimulator.feature.barbarian.UnarmoredDefence
import uk.laxd.dndSimulator.feature.rogue.SneakAttack
import java.util.*
import java.util.stream.Collectors
import kotlin.reflect.KClass

@Component
class FeatureFactory {

    private val features: MutableCollection<KClass<out Feature>> = ArrayList()

    fun createFeatures(config: CharacterConfig): Collection<Feature> {
        // TODO: Also populate manually added features via CharacterConfig
        return features.stream()
                .map { f -> f.java.newInstance() }
                .filter { f: Feature -> config.characterClasses.contains(f.characterClassRequired) }
                .filter { f: Feature -> f.levelRequirement <= config.getLevel(f.characterClassRequired) }
                .collect(Collectors.toList())
    }

    // TODO: Load all features via reflection instead?
    init {
        features.add(Rage::class)
        features.add(RecklessAttack::class)
        features.add(UnarmoredDefence::class)
        features.add(SneakAttack::class)
    }
}