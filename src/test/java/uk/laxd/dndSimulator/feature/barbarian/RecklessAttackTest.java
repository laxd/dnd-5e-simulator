package uk.laxd.dndSimulator.feature.barbarian;

import org.junit.Before;
import org.junit.Test;
import uk.laxd.dndSimulator.action.MeleeAttackAction;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.character.CharacterClass;
import uk.laxd.dndSimulator.equipment.Greatsword;
import uk.laxd.dndSimulator.equipment.Weapon;

import static org.junit.Assert.*;

public class RecklessAttackTest {

    private RecklessAttack recklessAttack = new RecklessAttack();

    private Character character;
    private Character target;
    private Weapon weapon;

    private MeleeAttackAction attackAction;


    @Before
    public void setUp() throws Exception {
        character = new Character("Steve", CharacterClass.BARBARIAN, 20);
        target = new Character("Alan", CharacterClass.BARBARIAN, 20);
        weapon = new Greatsword();
    }

    @Test
    public void testAttackingRecklesslyAddsAdvantage() throws Exception {
        attackAction = new MeleeAttackAction(character, weapon, target);

        recklessAttack.activate();

        recklessAttack.onAttackRoll(attackAction);

        assertTrue(attackAction.isWithAdvantage());
    }

    @Test
    public void testBeingAttackedRecklesslyAddsAdvantage() throws Exception {
        attackAction = new MeleeAttackAction(target, weapon, character);

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