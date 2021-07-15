package uk.laxd.dndSimulator.action

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.dice.Roll
import uk.laxd.dndSimulator.dice.RollType
import uk.laxd.dndSimulator.equipment.Weapon
import uk.laxd.dndSimulator.equipment.WeaponProperty
import kotlin.math.max

@Component
class WeaponAttackRollFactory {

    fun createAttackRoll(weapon: Weapon,
                         actor: Character,
                         rollType: RollType = RollType.NORMAL_ROLL
    ): Roll {
        var mod = weapon.toHitModifier

        // TODO: Ranged weapons
        mod += if(weapon.hasProperty(WeaponProperty.FINESSE)) {
            max(actor.getAbilityModifier(Ability.DEXTERITY), actor.getAbilityModifier(Ability.STRENGTH))
        }
        else {
            actor.getAbilityModifier(Ability.STRENGTH)
        }

        if(actor.hasProficiency(weapon)) {
            mod += actor.proficiencyBonus
        }

        mod += actor.proficiencyBonus

        return when (rollType) {
            RollType.ADVANTAGE -> Roll(Die.D20_Advantage, modifier = mod)
            RollType.DISADVANTAGE -> Roll(Die.D20_Disadvantage, modifier = mod)
            else -> Roll(Die.D20, modifier = mod)
        }
    }

}