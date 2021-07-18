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
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.config.ArmourFactory
import uk.laxd.dndSimulator.config.internal.CharacterConfig
import uk.laxd.dndSimulator.config.CharacterFactory
import uk.laxd.dndSimulator.config.WeaponFactory
import uk.laxd.dndSimulator.config.internal.CustomArmourConfig
import uk.laxd.dndSimulator.equipment.ArmourCategory
import uk.laxd.dndSimulator.equipment.CustomArmour
import uk.laxd.dndSimulator.equipment.StuddedLeatherArmour
import uk.laxd.dndSimulator.feature.Feature
import uk.laxd.dndSimulator.feature.FeatureFactory
import uk.laxd.dndSimulator.feature.barbarian.Rage
import uk.laxd.dndSimulator.proficiency.Proficiency
import uk.laxd.dndSimulator.proficiency.ProficiencyType
import java.util.stream.Stream

internal class CharacterFactoryTest {

    private val featureFactory: FeatureFactory = mockk(relaxed = true)
    private val armourFactory: ArmourFactory = mockk(relaxed = true)
    private val weaponFactory: WeaponFactory = mockk(relaxed = true)

    private lateinit var characterFactory: CharacterFactory

    private lateinit var config: CharacterConfig

    @BeforeEach
    internal fun setUp() {
        config = CharacterConfig("Steve", "Team A")

        characterFactory = CharacterFactory(featureFactory, armourFactory, weaponFactory)
    }

    @Test
    internal fun `basic values passed in constructor are set on Character`() {
        val characters = characterFactory.createCharacters(listOf(config))

        assertEquals("Steve", characters[0].name)
        assertEquals("Team A", characters[0].team)
    }

    @Test
    internal fun `hp is set to manually provided hp`() {
        config.hp = 25

        val characters = characterFactory.createCharacters(listOf(config))

        assertEquals(25, characters[0].maxHp)
    }

    @Test
    internal fun `hp is set automatically if no hp provided`() {
        val characters = characterFactory.createCharacters(listOf(config))

        assertNotEquals(0, characters[0].maxHp)
    }

    @Test
    internal fun `hp and maxHp is set to the same value`() {
        val characters = characterFactory.createCharacters(listOf(config))

        assertEquals(characters[0].hp, characters[0].maxHp)
    }

    @Test
    internal fun `AC is set to 10 if no armour equipped`() {
        val character = characterFactory.createCharacter(config)

        assertEquals(10, character.getArmourClass())
    }

    @Test
    internal fun `AC is set based on equipped armour`() {
        config.armour.add(CustomArmourConfig("Test armour", 12, false, null, armourCategory = ArmourCategory.LIGHT))

        every { armourFactory.createArmour(any()) }.returns(CustomArmour(
            "Test armour", 12, false, armourCategory = ArmourCategory.LIGHT
        ))

        val character = characterFactory.createCharacter(config)
        character.proficiencies.add(Proficiency("light-armour", ProficiencyType.ARMOUR))

        assertEquals(12, character.getArmourClass())
    }

    @Test
    internal fun `AC does not include equipped armour without proficiency`() {
        config.armour.add(CustomArmourConfig("Test armour", 12, false, null, armourCategory = ArmourCategory.LIGHT))

        every { armourFactory.createArmour(any()) }.returns(CustomArmour(
            "Test armour", 12, false, armourCategory = ArmourCategory.LIGHT
        ))

        val character = characterFactory.createCharacter(config)
        character.proficiencies.remove(Proficiency("light-armour", ProficiencyType.ARMOUR))

        assertEquals(10, character.getArmourClass())
    }

    @Test
    internal fun `AC is set based on equipped armour if proficient and includes additional based on ability scores`() {
        config.armour.add(CustomArmourConfig("Test armour", 12, true, null, armourCategory = ArmourCategory.LIGHT))
        config.abilityScores.put(Ability.DEXTERITY, 20)

        every { armourFactory.createArmour(any()) }.returns(CustomArmour(
            "Test armour", 12, true, armourCategory = ArmourCategory.LIGHT
        ))

        val character = characterFactory.createCharacter(config)
        character.proficiencies.add(Proficiency("light-armour", ProficiencyType.ARMOUR))

        // Base 12 AC, plus 5 from dex
        assertEquals(17, character.getArmourClass())
    }

    @Test
    internal fun `AC is set to override value if it is set`() {
        config = CharacterConfig("Steve", "Team A", overrideArmourClass = 25)
        config.armour.add(CustomArmourConfig("Test armour", 12, true, null, armourCategory = ArmourCategory.LIGHT))

        val character = characterFactory.createCharacter(config)

        assertEquals(25, character.getArmourClass())
    }

    @Test
    internal fun `features are created for each feature provided`() {
        val feature = Rage()

        every { featureFactory.createFeatures(config) }.returns(listOf(feature))

        val characters = characterFactory.createCharacters(listOf(config))

        assertThat(characters[0].features).contains(feature)
    }

    @Test
    internal fun `features have onCreate called for each feature provided`() {
        val feature: Feature = mockk(relaxed = true)

        every { featureFactory.createFeatures(config) }.returns(listOf(feature))

        characterFactory.createCharacters(listOf(config))

        verify { feature.onCreate(any()) }
    }

    @ParameterizedTest
    @MethodSource("proficiencyLevels")
    internal fun `proficiency bonus is set correctly based on level`(level: Int, prof: Int) {
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