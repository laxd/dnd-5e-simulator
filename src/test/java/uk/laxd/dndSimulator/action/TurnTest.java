package uk.laxd.dndSimulator.action;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.laxd.dndSimulator.character.Barbarian;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.character.GenericCharacter;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TurnTest {

    private Character character;
    private Character target;

    private TargetSelector targetSelector;

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
        when(targetSelector.getTarget()).thenReturn(null);
        Turn turn = new Turn(actionResolver, damageResolver, character, targetSelector);

        TurnOutcome turnOutcome = turn.doTurn();

        assertFalse(turnOutcome.isHit());
    }
}