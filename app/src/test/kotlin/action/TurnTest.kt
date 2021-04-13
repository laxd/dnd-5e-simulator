package uk.laxd.dndSimulator.action

import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.runner.RunWith
import org.mockito.Mock
import org.junit.Before
import org.junit.Test
import org.mockito.junit.MockitoJUnitRunner
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.event.*

@RunWith(MockitoJUnitRunner::class)
class TurnTest {
    private var character: Character? = null
    private var target: Character? = null
    private var targetSelector: TargetSelector? = null
    private val eventFactory = EncounterEventFactory()
    private val eventLogger: EventLogger = SimpleEventLogger()

    @Mock
    private val actionResolver: ActionResolver? = null

    @Before
    fun setUp() {
        character = Character("Steve", CharacterClass.BARBARIAN, 20)
        target = Character("Alan", CharacterClass.GENERIC_CHARACTER, 20)
        targetSelector = SimpleTargetSelector(target!!)
    }

    @Test
    fun testNoTargetResultsInNoHit() {
        targetSelector = SimpleTargetSelector(null)
        val turn = Turn(eventFactory, eventLogger, actionResolver!!, character!!, targetSelector!!)
        turn.doTurn()
        Assert.assertTrue(eventLogger.events.stream()
            .noneMatch { e -> e.type === EncounterEventType.MELEE_ATTACK })
    }

    @Test
    fun testTurnStartEventIsLoggedAtStartOfTurn() {
        val turn = Turn(eventFactory, eventLogger, actionResolver!!, character!!, targetSelector!!)
        turn.doTurn()
        Assert.assertTrue(eventLogger.events.size > 0)
        assertEquals(EncounterEventType.TURN_START, eventLogger.events.iterator().next().type)
    }
}