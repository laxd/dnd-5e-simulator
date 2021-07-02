package uk.laxd.dndSimulator.feature

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.config.CharacterConfig
import uk.laxd.dndSimulator.feature.barbarian.Rage
import uk.laxd.dndSimulator.feature.generic.ExtraAttack
import uk.laxd.dndSimulator.feature.rogue.SneakAttack

internal class FeatureFactoryTest {

    val featureRegistry: FeatureRegistry = FeatureRegistry()

    val featureFactory: FeatureFactory = FeatureFactory(featureRegistry)

    @Test
    internal fun `features in registry are used for feature factory`() {
        featureRegistry.register(Rage::class)

        val config = CharacterConfig("Character", "Team")
        config.addLevel(1, CharacterClass.BARBARIAN)

        val features = featureFactory.createFeatures(config)

        assertEquals(Rage::class, features[0]::class)
    }

    @Test
    internal fun `features are not returned if the character does not meet level requirements`() {
        featureRegistry.register(ExtraAttack::class)

        val config = CharacterConfig("Character", "Team")
        config.addLevel(1, CharacterClass.BARBARIAN)

        val features = featureFactory.createFeatures(config)

        assertThat(features).isEmpty()
    }

    @Test
    internal fun `features are not returned if the character does not class requirements`() {
        featureRegistry.register(ExtraAttack::class)

        val config = CharacterConfig("Character", "Team")
        config.addLevel(10, CharacterClass.ROGUE)

        val features = featureFactory.createFeatures(config)

        assertThat(features).isEmpty()
    }

    @Test
    internal fun `features include all features for all classes that match requirements`() {
        featureRegistry.register(ExtraAttack::class)
        featureRegistry.register(SneakAttack::class)

        val config = CharacterConfig("Character", "Team")
        config.addLevel(10, CharacterClass.BARBARIAN)
        config.addLevel(1, CharacterClass.ROGUE)

        val features = featureFactory.createFeatures(config)

        assertThat(features).hasSize(2)
    }
}