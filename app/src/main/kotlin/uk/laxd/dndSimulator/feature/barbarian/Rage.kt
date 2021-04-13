package uk.laxd.dndSimulator.feature.barbarian

import uk.laxd.dndSimulator.feature.ActivatedFeature
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.action.DamageType
import uk.laxd.dndSimulator.action.ActionType
import uk.laxd.dndSimulator.character.Character

class Rage : ActivatedFeature("Rage") {
    private var ragesPerDay = 0
    private var ragesRemaining = 0
    private var rageDamageBonus = 0
    private var rageRounds = 0

    override fun onCreate(character: Character) {
        // Assume we are ALWAYS raging
        activate()
        val level = character!!.level
        if (level <= 2) {
            ragesPerDay = 2
        } else if (level <= 5) {
            ragesPerDay = 3
        } else if (level <= 11) {
            ragesPerDay = 4
        } else if (level <= 16) {
            ragesPerDay = 5
        } else if (level <= 19) {
            ragesPerDay = 6
        } else if (level == 20) {
            ragesPerDay = -1
        }
        ragesRemaining = ragesPerDay
        rageDamageBonus = if (level <= 8) {
            2
        } else if (level <= 15) {
            3
        } else {
            4
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

    override fun onDamageRoll(action: MeleeAttackAction) {
        // TODO: This just increments the damage of the weapon? Need to find the damage roll
        // for just the weapon somehow, or set it separately
        val damageType = action.weapon.damageType
        action.attackDamage.addAmount(damageType, 2)
    }

    override fun onDamageRollReceived(action: MeleeAttackAction) {
        val damage = action.attackDamage
        damage.addAmount(DamageType.PIERCING, -damage.getAmount(DamageType.PIERCING) / 2)
        damage.addAmount(DamageType.SLASHING, -damage.getAmount(DamageType.SLASHING) / 2)
        damage.addAmount(DamageType.BLUDGEONING, -damage.getAmount(DamageType.BLUDGEONING) / 2)
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