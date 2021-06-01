package uk.laxd.dndSimulator.feature.barbarian

import uk.laxd.dndSimulator.feature.ActivatedFeature
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.action.ActionType
import uk.laxd.dndSimulator.action.AttackAction
import uk.laxd.dndSimulator.character.Character

class Rage : ActivatedFeature("Rage") {
    private var ragesPerDay = 0
    private var ragesRemaining = 0
    private var rageDamageBonus = 0
    private var rageRounds = 0

    override fun onCreate(character: Character) {
        // TODO: Change to activating as a bonus action
        // Assume we are ALWAYS raging
        activate()
        val level = character!!.characterClassLevels[CharacterClass.BARBARIAN] ?: 0
        ragesPerDay = when (level) {
            in 1..2 -> 2
            in 3..5 -> 3
            in 6..11 -> 4
            in 12..16 -> 5
            in 17..19 -> 6
            20 -> -1
            else -> 0
        }
        ragesRemaining = ragesPerDay
        rageDamageBonus = when (level) {
            0 -> 0
            in 1..8 -> 2
            in 9..15 -> 3
            else -> 4
        }
    }

    override fun activate() {
        super.activate()
        rageRounds = 0
        ragesRemaining--
    }

    override val characterClassRequired: CharacterClass
        get() = CharacterClass.BARBARIAN

    override fun onTurnStart(character: Character) {
        if (isActive) {
            rageRounds++
            if (rageRounds >= MAX_RAGE_ROUNDS) {
                deactivate()
            }
        }
    }

    override fun onDamageRoll(action: AttackAction) {
        if(action !is MeleeAttackAction) {
            return
        }

        val damageType = action.weapon.damageType
        action.attackDamage.addDamage(this, damageType, 2)
    }

    override fun getResistances(): Collection<DamageType> {
        if(isActive) {
            return listOf(
                DamageType.PIERCING,
                DamageType.SLASHING,
                DamageType.BLUDGEONING
            )
        }

        return listOf()
    }

    override fun onAbilityCheck(character: Character) {
        if (isActive) {
            // If strength check, +2
        }
    }

    override fun onLongRest(character: Character) {
        ragesRemaining = ragesPerDay
    }

    override val actionType: ActionType
        get() = ActionType.BONUS_ACTION

    companion object {
        private const val MAX_RAGE_ROUNDS = 10
    }
}