package uk.laxd.dndSimulator.feature.barbarian

import io.mockk.mockk
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.laxd.dndSimulator.equipment.Weapon
import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.equipment.Greatsword
import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.action.WeaponAttackRollFactory
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.character.CharacterClass
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RageTest {

    private val attackRollFactory: WeaponAttackRollFactory = mockk()

    private val rage = Rage()
    private lateinit var character: Character
    private lateinit var target: Character
    private lateinit var weapon: Weapon

    @BeforeEach
    fun setUp() {
        character = Character("Steve", "Team A")
        target = Character("Alan", "Team B")
        weapon = Greatsword()

        character.addLevel(CharacterClass.BARBARIAN, 1)

        rage.onCreate(character)
    }

    @Test
    fun `Rage damage bonus is applied`() {
        val attackAction = MeleeAttackAction(attackRollFactory, character, weapon, target)
        attackAction.attackDamage.addDamage(weapon, DamageType.SLASHING, 10)
        rage.onDamageRoll(attackAction)
        assertEquals(12, attackAction.attackDamage.getAmount(DamageType.SLASHING).toLong())
    }

    @Test
    fun `Rage provides resistance to slashing bludgeoning and piercing damage when active`() {
        rage.activate()
        val resistances = rage.getResistances()

        assertEquals(3, resistances.size)
        assertTrue(resistances.contains(DamageType.PIERCING))
        assertTrue(resistances.contains(DamageType.BLUDGEONING))
        assertTrue(resistances.contains(DamageType.SLASHING))
    }

    @Test
    fun `Rage provides no resistances when not active`() {
        val resistances = rage.getResistances()

        assertTrue(resistances.isEmpty())
    }
}