package uk.laxd.dndSimulator.character

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import uk.laxd.dndSimulator.ability.Ability

internal class CharacterTest {
    @Test
    internal fun `getAbilityModifier returns 0 when no value exists for that ability`() {
        val character = Character("Steve", "Team A")

        assertEquals(0, character.getAbilityModifier(Ability.STRENGTH))
    }

    @Test
    internal fun `getAbilityModifier returns correct value when value is set`() {
        val character = Character("Steve", "Team A")
        character.abilities[Ability.STRENGTH] = 20

        assertEquals(5, character.getAbilityModifier(Ability.STRENGTH))
    }

    @Test
    internal fun `getAbilityModifier returns correct value when value less than 10 is set`() {
        val character = Character("Steve", "Team A")
        character.abilities[Ability.STRENGTH] = 8

        assertEquals(-1, character.getAbilityModifier(Ability.STRENGTH))
    }
}