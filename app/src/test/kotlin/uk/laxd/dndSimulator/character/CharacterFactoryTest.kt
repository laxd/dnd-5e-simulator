package uk.laxd.dndSimulator.character

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import uk.laxd.dndSimulator.config.CharacterConfig
import uk.laxd.dndSimulator.feature.Feature
import uk.laxd.dndSimulator.feature.FeatureFactory
import uk.laxd.dndSimulator.feature.barbarian.Rage
import java.util.stream.Stream

internal class CharacterFactoryTest {

    val featureFactory: FeatureFactory = mockk()

    lateinit var characterFactory: CharacterFactory

    @BeforeEach
    internal fun setUp() {
        characterFactory = CharacterFactory(featureFactory)

        every { featureFactory.createFeatures(any()) }.returns(listOf())
    }

    @Test
    internal fun `basic values passed in constructor are set on Character`() {
        val config = CharacterConfig("Steve", "Team A")

        val characters = characterFactory.createCharacters(listOf(config))

        assertEquals("Steve", characters[0].name)
        assertEquals("Team A", characters[0].team)
    }

    @Test
    internal fun `hp is set to manually provided hp`() {
        val config = CharacterConfig("Steve", "Team A")
        config.hp = 25

        val characters = characterFactory.createCharacters(listOf(config))

        assertEquals(25, characters[0].maxHp)
    }

    @Test
    internal fun `hp is set automatically if no hp provided`() {
        val config = CharacterConfig("Steve", "Team A")

        val characters = characterFactory.createCharacters(listOf(config))

        assertNotEquals(0, characters[0].maxHp)
    }

    @Test
    internal fun `hp and maxHp is set to the same value`() {
        val config = CharacterConfig("Steve", "Team A")

        val characters = characterFactory.createCharacters(listOf(config))

        assertEquals(characters[0].hp, characters[0].maxHp)
    }

    @Test
    internal fun `features are created for each feature provided`() {
        val config = CharacterConfig("Steve", "Team A")

        val feature = Rage()

        every { featureFactory.createFeatures(config) }.returns(listOf(feature))

        val characters = characterFactory.createCharacters(listOf(config))

        assertThat(characters[0].features).contains(feature)
    }

    @Test
    internal fun `features have onCreate called for each feature provided`() {
        val config = CharacterConfig("Steve", "Team A")

        val feature: Feature = mockk(relaxed = true)

        every { featureFactory.createFeatures(config) }.returns(listOf(feature))

        val characters = characterFactory.createCharacters(listOf(config))

        verify { feature.onCreate(any()) }
    }

    @ParameterizedTest
    @MethodSource("proficiencyLevels")
    internal fun `proficiency bonus is set correctly based on level`(level: Int, prof: Int) {
        val config = CharacterConfig("Steve", "Team A")
        config.addLevel(level, CharacterClass.BARBARIAN)

        val characters = characterFactory.createCharacters(listOf(config))

        assertEquals(prof, characters[0].proficiencyBonus)
    }

    companion object {
        @JvmStatic
        fun proficiencyLevels() = Stream.of(
            Arguments.of(1, 2),
            Arguments.of(2, 2),
            Arguments.of(3, 2),
            Arguments.of(4, 2),
            Arguments.of(5, 3),
            Arguments.of(6, 3),
            Arguments.of(7, 3),
            Arguments.of(8, 3),
            Arguments.of(9, 4),
            Arguments.of(10, 4),
            Arguments.of(11, 4),
            Arguments.of(12, 4),
            Arguments.of(13, 5),
            Arguments.of(14, 5),
            Arguments.of(15, 5),
            Arguments.of(16, 5),
            Arguments.of(17, 6),
            Arguments.of(18, 6),
            Arguments.of(19, 6),
            Arguments.of(20, 6),
        )
    }
}