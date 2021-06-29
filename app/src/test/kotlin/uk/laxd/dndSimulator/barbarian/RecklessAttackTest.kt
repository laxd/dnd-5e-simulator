package uk.laxd.dndSimulator.feature.barbarian

import io.mockk.mockk
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.laxd.dndSimulator.equipment.Weapon
import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.action.WeaponAttackRollFactory
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.equipment.Greatsword

class RecklessAttackTest {

    private val attackRollFactory: WeaponAttackRollFactory = mockk()

    private val recklessAttack = RecklessAttack()
    private lateinit var character: Character
    private lateinit var target: Character
    private lateinit var weapon: Weapon

    @BeforeEach
    fun setUp() {
        character = Character("Steve", "Team A")
        target = Character("Alan", "Team B")
        weapon = Greatsword()
    }

    @Test
    fun testAttackingRecklesslyAddsAdvantage() {
        val attackAction = MeleeAttackAction(attackRollFactory, character, weapon, target)
        recklessAttack.activate()
        recklessAttack.onAttackRoll(attackAction)
        Assert.assertTrue(attackAction.withAdvantage)
    }

    @Test
    fun testBeingAttackedRecklesslyAddsAdvantage() {
        val attackAction = MeleeAttackAction(attackRollFactory, target, weapon, character)
        recklessAttack.activate()
        recklessAttack.onAttackRollReceiving(attackAction)
        Assert.assertTrue(attackAction.withAdvantage)
    }

    @Test
    fun testRecklessAttackIsResetAtStartOfTurn() {
        recklessAttack.activate()
        recklessAttack.onTurnStart(character)
        Assert.assertFalse(recklessAttack.isActive)
    }
}