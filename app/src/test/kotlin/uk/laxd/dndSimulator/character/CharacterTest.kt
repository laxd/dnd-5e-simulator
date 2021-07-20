package uk.laxd.dndSimulator.character

import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.config.internal.CharacterConfig
import uk.laxd.dndSimulator.config.internal.CustomArmourConfig
import uk.laxd.dndSimulator.equipment.ArmourCategory
import uk.laxd.dndSimulator.equipment.CustomArmour
import uk.laxd.dndSimulator.proficiency.Proficiency
import uk.laxd.dndSimulator.proficiency.ProficiencyType

internal class CharacterTest {

    private val character = Character("Steve", "Team A")

    @Test
    internal fun `getAbilityModifier returns 0 when no value exists for that ability`() {
        assertEquals(0, character.getAbilityModifier(Ability.STRENGTH))
    }

    @Test
    internal fun `getAbilityModifier returns correct value when value is set`() {
        character.abilities[Ability.STRENGTH] = 20

        assertEquals(5, character.getAbilityModifier(Ability.STRENGTH))
    }

    @Test
    internal fun `getAbilityModifier returns correct value when value less than 10 is set`() {
        character.abilities[Ability.STRENGTH] = 8

        assertEquals(-1, character.getAbilityModifier(Ability.STRENGTH))
    }

    @Test
    internal fun `AC is set based on equipped armour`() {
        val armour = CustomArmour("Test armour", 12, false, armourCategory = ArmourCategory.LIGHT)
        armour.isEquipped = true
        character.inventory.add(armour)

        character.proficiencies.add(Proficiency("light-armour", ProficiencyType.ARMOUR))

        assertEquals(12, character.getArmourClass())
    }

    @Test
    internal fun `AC does not include equipped armour without proficiency`() {
        val armour = CustomArmour("Test armour", 12, false, armourCategory = ArmourCategory.LIGHT)
        armour.isEquipped = true
        character.inventory.add(armour)

        character.proficiencies.remove(Proficiency("light-armour", ProficiencyType.ARMOUR))

        assertEquals(10, character.getArmourClass())
    }

    @Test
    internal fun `AC is set based on equipped armour if proficient and includes additional based on ability scores`() {
        val armour = CustomArmour("Test armour", 12, true, armourCategory = ArmourCategory.LIGHT)
        armour.isEquipped = true
        character.inventory.add(armour)

        character.abilities[Ability.DEXTERITY] = 20
        character.proficiencies.add(Proficiency("light-armour", ProficiencyType.ARMOUR))

        // Base 12 AC, plus 5 from dex
        assertEquals(17, character.getArmourClass())
    }

    @Test
    internal fun `AC is set to 10 if no armour equipped`() {
        character.inventory.clear()

        assertEquals(10, character.getArmourClass())
    }

    @Test
    internal fun `AC is set to override value if it is set`() {
        character.overrideArmourClass = 25

        assertEquals(25, character.getArmourClass())
    }
}