package uk.laxd.dndSimulator.action

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.dice.Roll
import uk.laxd.dndSimulator.equipment.CustomWeapon
import uk.laxd.dndSimulator.equipment.Greatsword
import uk.laxd.dndSimulator.equipment.Weapon

internal class MeleeAttackActionTest {

    private lateinit var actor: Character
    private lateinit var target: Character
    private lateinit var weapon: Weapon
    private lateinit var meleeAttackAction: MeleeAttackAction

    private val attackRollFactory: WeaponAttackRollFactory = mockk()

    @BeforeEach
    internal fun setUp() {
        actor = Character("Actor Character", "Team A")
        target = Character("Target Character", "Team B")
        weapon = Greatsword()
        meleeAttackAction = MeleeAttackAction(attackRollFactory, actor, weapon, target)
    }

    @Test
    internal fun `An attack that doesn't beat AC results in a miss`() {
        every { attackRollFactory.createAttackRoll(weapon, actor) }
            .returns(Roll(Die.fixedRoll(10, 20), modifier = 0))

        target.armorClass = 15

        meleeAttackAction.performAction()

        assertEquals(AttackOutcome.MISS, meleeAttackAction.outcome)
    }

    @Test
    internal fun `An attack that rolls a 1 results in a miss`() {
        every { attackRollFactory.createAttackRoll(weapon, actor) }
            .returns(Roll(Die.fixedRoll(1, 20), modifier = 100))

        target.armorClass = 15

        meleeAttackAction.performAction()

        assertEquals(AttackOutcome.MISS, meleeAttackAction.outcome)
    }

    @Test
    internal fun `An attack that rolls a 20 results in a crit`() {
        every { attackRollFactory.createAttackRoll(weapon, actor) }
            .returns(Roll(Die.fixedRoll(20, 20), modifier = 0))

        target.armorClass = 21

        meleeAttackAction.performAction()

        assertEquals(AttackOutcome.CRIT, meleeAttackAction.outcome)
    }
}