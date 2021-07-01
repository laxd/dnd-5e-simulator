package uk.laxd.dndSimulator.action

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.laxd.dndSimulator.character.Character

internal class SimpleTargetSelectorTest {
    lateinit var targetSelector: TargetSelector

    @Test
    internal fun `findTarget chooses target from another team`() {
        val characterA = createCharacter("Steve", "Team A")
        val characterB = createCharacter("Dave", "Team B")

        targetSelector = SimpleTargetSelector(listOf(characterA, characterB))

        val result = targetSelector.findTarget(characterA)

        assertEquals(characterB, result)
    }

    @Test
    internal fun `findTarget returns null if no targets available`() {
        val characterA = createCharacter("Steve", "Team A")

        targetSelector = SimpleTargetSelector(listOf(characterA))

        val result = targetSelector.findTarget(characterA)

        assertNull(result)
    }

    @Test
    internal fun `findTarget only returns targets from the other team`() {
        val actor = createCharacter("Actor", "Team A")

        targetSelector = SimpleTargetSelector(listOf(
            actor,
            createCharacter("Character 2", "Team A"),
            createCharacter("Character 3", "Team A"),
            createCharacter("Character 4", "Team B")
        ))

        val result = targetSelector.findTarget(actor)

        assertEquals("Team B", result?.team)
    }

    fun createCharacter(name: String, team: String): Character {
        return Character(name, team).apply {
            maxHp = 1
            hp = 1
        }
    }
}