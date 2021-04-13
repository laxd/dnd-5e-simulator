package uk.laxd.dndSimulator.feature

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.character.CharacterConfig
import uk.laxd.dndSimulator.feature.barbarian.Rage
import uk.laxd.dndSimulator.feature.barbarian.RecklessAttack
import uk.laxd.dndSimulator.feature.barbarian.UnarmoredDefence
import uk.laxd.dndSimulator.feature.rogue.SneakAttack
import java.util.*

@Component
class FeatureFactory {

    private val features: MutableCollection<Class<out Feature>> = ArrayList()

    fun createFeatures(config: CharacterConfig): Collection<Feature> {
        // TODO: Fix this to return features as required
        // TODO: Also populate manually added features via CharacterConfig
//        return features.stream()
//                .map { f: Feature -> Class.forName(f).newInstance() }
//                .filter { f: Feature -> config.characterClasses.contains(f.characterClassRequired) }
//                .filter { f: Feature -> f.levelRequirement <= config.getLevel(f.characterClassRequired) }
//                .collect(Collectors.toList())
        return Collections.emptyList();
    }

    // TODO: Load all features via reflection instead?
    init {
        features.add(Rage::class.java)
        features.add(RecklessAttack::class.java)
        features.add(UnarmoredDefence::class.java)
        features.add(SneakAttack::class.java)
    }
}