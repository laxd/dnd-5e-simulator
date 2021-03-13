package uk.laxd.dndSimulator.feature.barbarian;

import org.junit.Before;
import org.junit.Test;
import uk.laxd.dndSimulator.action.AttackAction;
import uk.laxd.dndSimulator.character.Barbarian;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.equipment.Greatsword;
import uk.laxd.dndSimulator.equipment.Weapon;

import static org.junit.Assert.*;

public class RecklessAttackTest {

    private RecklessAttack recklessAttack = new RecklessAttack();

    private Character character;
    private Character target;
    private Weapon weapon;

    private AttackAction attackAction;


    @Before
    public void setUp() throws Exception {
        character = new Barbarian(20, "Steve");
        target = new Barbarian(20, "Alan");
        weapon = new Greatsword();
    }

    @Test
    public void testAttackingRecklesslyAddsAdvantage() throws Exception {
        attackAction = new AttackAction(character, weapon, target);

        recklessAttack.activate();

        recklessAttack.onAttackRoll(attackAction);

        assertTrue(attackAction.isWithAdvantage());
    }

    @Test
    public void testBeingAttackedRecklesslyAddsAdvantage() throws Exception {
        attackAction = new AttackAction(target, weapon, character);

        recklessAttack.activate();

        recklessAttack.onAttackRollReceiving(attackAction);

        assertTrue(attackAction.isWithAdvantage());
    }

    @Test
    public void testRecklessAttackIsResetAtStartOfTurn() throws Exception {
        recklessAttack.activate();

        recklessAttack.onTurnStart(character);

        assertFalse(recklessAttack.isActive());
    }
}