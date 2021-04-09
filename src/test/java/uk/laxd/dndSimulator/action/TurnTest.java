package uk.laxd.dndSimulator.action;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.laxd.dndSimulator.character.Barbarian;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.character.GenericCharacter;
import uk.laxd.dndSimulator.event.EncounterEventFactory;
import uk.laxd.dndSimulator.event.EncounterEventType;
import uk.laxd.dndSimulator.event.EventLogger;
import uk.laxd.dndSimulator.event.SimpleEventLogger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TurnTest {

    private Character character;
    private Character target;

    private TargetSelector targetSelector;

    private EncounterEventFactory eventFactory = new EncounterEventFactory();

    private EventLogger eventLogger = new SimpleEventLogger();

    @Mock
    private ActionResolver actionResolver;

    @Mock
    private DamageResolver damageResolver;

    @Before
    public void setUp() throws Exception {
        this.character = new Barbarian(20, "Steve");
        this.target = new GenericCharacter(20, "Alan");

        this.targetSelector = new SimpleTargetSelector(target);
    }

    @Test
    public void testNoTargetResultsInNoHit() throws Exception {
        this.targetSelector = new SimpleTargetSelector(null);
        Turn turn = new Turn(eventFactory, eventLogger, actionResolver, damageResolver, character, targetSelector);

        turn.doTurn();

        assertTrue(eventLogger.getEvents().stream()
                .noneMatch(e -> e.getType() == EncounterEventType.MELEE_ATTACK));
    }
}