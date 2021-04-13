package uk.laxd.dndSimulator.feature.barbarian

import org.junit.Assert
import uk.laxd.dndSimulator.equipment.Weapon
import uk.laxd.dndSimulator.action.MeleeAttackAction
import org.junit.Before
import org.junit.Test
import kotlin.Throws
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.equipment.Greatsword
import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.character.Character
import java.lang.Exception

class RageTest {

    private val rage = Rage()
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
    fun testRageDamageIsApplied() {
        val attackAction = MeleeAttackAction(character!!, weapon, target!!)
        attackAction.addAttackDamage(DamageType.SLASHING, 10)
        rage.onDamageRoll(attackAction)
        Assert.assertEquals(12, attackAction.attackDamage.getAmount(DamageType.SLASHING).toLong())
    }

    @Test
    fun testRageDamageReductionApplies() {
        val attackAction = MeleeAttackAction(target!!, weapon, character!!)
        attackAction.addAttackDamage(DamageType.SLASHING, 10)
        rage.onDamageRollReceived(attackAction)
        Assert.assertEquals(5, attackAction.attackDamage.getAmount(DamageType.SLASHING).toLong())
    }

    @Test
    fun testRageDamageReductionDoesNotApply() {
        val attackAction = MeleeAttackAction(target!!, weapon, character!!)
        attackAction.addAttackDamage(DamageType.FIRE, 10)
        rage.onDamageRollReceived(attackAction)
        Assert.assertEquals(10, attackAction.attackDamage.getAmount(DamageType.FIRE).toLong())
    }
}