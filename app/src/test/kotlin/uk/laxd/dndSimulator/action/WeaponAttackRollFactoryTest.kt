package uk.laxd.dndSimulator.action

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.dice.RollType
import uk.laxd.dndSimulator.equipment.*

internal class WeaponAttackRollFactoryTest {

    val weaponAttackRollFactory = WeaponAttackRollFactory()

    lateinit var actor: Character
    lateinit var weapon: Weapon

    @BeforeEach
    internal fun setUp() {
        actor = Character("Steve", "Team A")
        actor.proficiencyBonus = 0 // Remove proficiency for these tests for simplicity

        weapon = CustomWeapon(
            "Weapon",
            DamageType.SLASHING,
            6,
            2,
            0,
            0,
            listOf(),
            5,
            1.0
        )
    }

    @Test
    internal fun `Roll includes proficiency bonus`() {
        actor.proficiencyBonus = 5

        val roll = weaponAttackRollFactory.createAttackRoll(weapon, actor)

        assertEquals(5, roll.modifier)
    }

    @Test
    internal fun `Roll includes weapon modifier`() {
        val roll = weaponAttackRollFactory.createAttackRoll(weapon, actor)

        assertEquals(weapon.toHitModifier, roll.modifier)
    }

    @Test
    internal fun `Roll includes advantage if given rollType = advantage`() {
        val roll = weaponAttackRollFactory.createAttackRoll(weapon, actor, RollType.ADVANTAGE)

        assertEquals(Die.D20_Advantage, roll.dice[0])
    }

    @Test
    internal fun `Roll includes advantage if rollType = disadvantage`() {
        val roll = weaponAttackRollFactory.createAttackRoll(weapon, actor, RollType.DISADVANTAGE)

        assertEquals(Die.D20_Disadvantage, roll.dice[0])
    }

    @Test
    internal fun `Roll includes strength modifier for weapons`() {
        actor.abilities[Ability.STRENGTH] = 20

        val roll = weaponAttackRollFactory.createAttackRoll(weapon, actor)

        assertEquals(5, roll.modifier)
    }

    @Test
    internal fun `Dexterity is used if higher for finesse weapons`() {
        weapon = CustomWeapon(
            "Weapon",
            DamageType.SLASHING,
            6,
            2,
            0,
            0,
            listOf(WeaponProperty.FINESSE),
            5,
            1.0
        )

        actor.abilities[Ability.STRENGTH] = 10
        actor.abilities[Ability.DEXTERITY] = 20

        val roll = weaponAttackRollFactory.createAttackRoll(weapon, actor)

        assertEquals(5, roll.modifier)
    }

    @Test
    internal fun `Dexterity is NOT used for weapons that are NOT finesse weapons`() {
        actor.abilities[Ability.STRENGTH] = 10
        actor.abilities[Ability.DEXTERITY] = 20

        val roll = weaponAttackRollFactory.createAttackRoll(weapon, actor)

        assertEquals(0, roll.modifier)
    }

    @Test
    internal fun `Strength is used if higher for finesse weapons`() {
        weapon = CustomWeapon(
            "Weapon",
            DamageType.SLASHING,
            6,
            2,
            0,
            0,
            listOf(WeaponProperty.FINESSE),
            5,
            1.0
        )

        actor.abilities[Ability.STRENGTH] = 20
        actor.abilities[Ability.DEXTERITY] = 10

        val roll = weaponAttackRollFactory.createAttackRoll(weapon, actor)

        assertEquals(5, roll.modifier)
    }

    @Test
    internal fun `Proficiency bonus is added to attack rolls`() {
        actor.proficiencyBonus = 100

        val roll = weaponAttackRollFactory.createAttackRoll(weapon, actor)

        assertEquals(100, roll.modifier)
    }
}