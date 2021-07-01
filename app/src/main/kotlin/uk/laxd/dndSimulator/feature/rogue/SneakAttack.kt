package uk.laxd.dndSimulator.feature.rogue

import uk.laxd.dndSimulator.action.AttackAction
import uk.laxd.dndSimulator.feature.StaticFeature
import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.equipment.WeaponProperty
import uk.laxd.dndSimulator.dice.Roll
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.dice.RollType
import kotlin.math.ceil

class SneakAttack : StaticFeature("Sneak Attack") {
    private var hasSneakAttacked = false
    private var diceCount = 0

    override fun onCreate(character: Character) {
        diceCount = ceil((character.level / 2).toDouble()).toInt()
    }

    override fun onDamageRoll(action: AttackAction) {
        if(action !is MeleeAttackAction) {
            return
        }

        if (!(action.weapon.hasProperty(WeaponProperty.FINESSE) || action.weapon.hasProperty(WeaponProperty.RANGE))) {
            // Sneak attack only applies to finesse/ranged attacks
            return
        }
        if (hasSneakAttacked) {
            // 1 sneak attack per turn
            return
        }
        if (action.rollType != RollType.ADVANTAGE) {
            // Sneak attack only applies if you have adv
            // TODO: Or an ally within 5 ft of target
            return
        }

        for (i in 0 until diceCount) {
            action.damageRoll?.addDie(Die.D6)
        }

        hasSneakAttacked = true
    }

    override fun onTurnEnd(character: Character) {
        hasSneakAttacked = false
    }

    override val characterClassRequired: CharacterClass
        get() = CharacterClass.ROGUE
}