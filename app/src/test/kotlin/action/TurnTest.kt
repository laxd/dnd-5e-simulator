package uk.laxd.dndSimulator.action

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.event.*

class TurnTest {
    private var character: Character? = null
    private var target: Character? = null
    private var targetSelector: TargetSelector? = null
    private val eventLogger: EventLogger = EventLogger.instance
    private val actionFactory = ActionFactory()
    private val actionResolver: ActionResolver = mockk()

    @BeforeEach
    fun setUp() {
        character = Character("Steve", "Team A")
        target = Character("Alan", "Team B")
        targetSelector = SimpleTargetSelector(listOf(character!!, target!!))

        every { actionResolver.resolve(any()) }.returns(Unit)
    }

    @Test
    fun testNoTargetResultsInNoHit() {
        targetSelector = SimpleTargetSelector(listOf())
        val turn = Turn(actionFactory, actionResolver, character!!, targetSelector!!)
        turn.doTurn()
        assertTrue(eventLogger.events.stream()
            .noneMatch { e -> e.type === EncounterEventType.MELEE_ATTACK })
    }

    @Test
    fun testTurnStartEventIsLoggedAtStartOfTurn() {
        val turn = Turn(actionFactory, actionResolver, character!!, targetSelector!!)

        justRun { actionResolver.resolve(any()) }

        turn.doTurn()
        assertTrue(eventLogger.events.isNotEmpty())
        assertEquals(EncounterEventType.TURN_START, eventLogger.events.iterator().next().type)
    }
}