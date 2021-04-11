package uk.laxd.dndSimulator.action;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.character.CharacterClass;
import uk.laxd.dndSimulator.event.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TurnTest {

    private Character character;
    private Character target;

    private TargetSelector targetSelector;

    private EncounterEventFactory eventFactory = new EncounterEventFactory();

    private EventLogger eventLogger = new SimpleEventLogger();

    @Mock
    private ActionResolver actionResolver;

    @Before
    public void setUp() throws Exception {
        this.character = new Character("Steve", CharacterClass.BARBARIAN, 20);
        this.target = new Character("Alan", CharacterClass.GENERIC_CHARACTER, 20);

        this.targetSelector = new SimpleTargetSelector(target);
    }

    @Test
    public void testNoTargetResultsInNoHit() throws Exception {
        this.targetSelector = new SimpleTargetSelector(null);
        Turn turn = new Turn(eventFactory, eventLogger, actionResolver, character, targetSelector);

        turn.doTurn();

        assertTrue(eventLogger.getEvents().stream()
                .noneMatch(e -> e.getType() == EncounterEventType.MELEE_ATTACK));
    }

    @Test
    public void testTurnStartEventIsLoggedAtStartOfTurn() throws Exception {
        Turn turn = new Turn(eventFactory, eventLogger, actionResolver, character, targetSelector);

        turn.doTurn();

        assertTrue(eventLogger.getEvents().size() > 0);
        assertEquals(EncounterEventType.TURN_START, eventLogger.getEvents().iterator().next().getType());
    }

}