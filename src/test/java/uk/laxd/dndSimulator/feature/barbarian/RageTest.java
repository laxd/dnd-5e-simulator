package uk.laxd.dndSimulator.feature.barbarian;

import org.junit.Before;
import org.junit.Test;
import uk.laxd.dndSimulator.action.MeleeAttackAction;
import uk.laxd.dndSimulator.action.DamageType;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.character.CharacterClass;
import uk.laxd.dndSimulator.equipment.Greatsword;
import uk.laxd.dndSimulator.equipment.Weapon;

import static org.junit.Assert.*;

public class RageTest {

    private Rage rage = new Rage();

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
    public void testRageDamageIsApplied() throws Exception {
        attackAction = new MeleeAttackAction(character, weapon, target);

        attackAction.addAttackDamage(DamageType.SLASHING, 10);

        rage.onDamageRoll(attackAction);

        assertEquals(12, attackAction.getAttackDamage().getAmount(DamageType.SLASHING));
    }

    @Test
    public void testRageDamageReductionApplies() throws Exception {
        attackAction = new MeleeAttackAction(target, weapon, character);

        attackAction.addAttackDamage(DamageType.SLASHING, 10);

        rage.onDamageRollReceived(attackAction);

        assertEquals(5, attackAction.getAttackDamage().getAmount(DamageType.SLASHING));
    }

    @Test
    public void testRageDamageReductionDoesNotApply() throws Exception {
        attackAction = new MeleeAttackAction(target, weapon, character);

        attackAction.addAttackDamage(DamageType.FIRE, 10);

        rage.onDamageRollReceived(attackAction);

        assertEquals(10, attackAction.getAttackDamage().getAmount(DamageType.FIRE));
    }
}