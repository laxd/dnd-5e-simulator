package uk.laxd.dndSimulator.feature.barbarian

import org.junit.Assert
import uk.laxd.dndSimulator.equipment.Weapon
import uk.laxd.dndSimulator.action.MeleeAttackAction
import org.junit.Before
import org.junit.Test
import uk.laxd.dndSimulator.character.Character
import kotlin.Throws
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.equipment.Greatsword
import java.lang.Exception

class RecklessAttackTest {

    private val recklessAttack = RecklessAttack()
    private var character: Character? = null
    private var target: Character? = null
    private var weapon: Weapon? = null

    @Before
    fun setUp() {
        character = Character("Steve", CharacterClass.BARBARIAN, 20)
        target = Character("Alan", CharacterClass.BARBARIAN, 20)
        weapon = Greatsword()
    }

    @Test
    fun testAttackingRecklesslyAddsAdvantage() {
        val attackAction = MeleeAttackAction(character!!, weapon, target!!)
        recklessAttack.activate()
        recklessAttack.onAttackRoll(attackAction)
        Assert.assertTrue(attackAction.isWithAdvantage())
    }

    @Test
    fun testBeingAttackedRecklesslyAddsAdvantage() {
        val attackAction = MeleeAttackAction(target!!, weapon, character!!)
        recklessAttack.activate()
        recklessAttack.onAttackRollReceiving(attackAction)
        Assert.assertTrue(attackAction.isWithAdvantage())
    }

    @Test
    fun testRecklessAttackIsResetAtStartOfTurn() {
        recklessAttack.activate()
        recklessAttack.onTurnStart(character)
        Assert.assertFalse(recklessAttack.isActive)
    }
}