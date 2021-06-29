package uk.laxd.dndSimulator.action

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.Die
import uk.laxd.dndSimulator.dice.Roll
import uk.laxd.dndSimulator.equipment.Weapon
import uk.laxd.dndSimulator.equipment.WeaponProperty
import kotlin.math.max

@Component
class WeaponAttackRollFactory {

    fun createAttackRoll(weapon: Weapon,
                         actor: Character,
                         withAdvantage: Boolean = false,
                         withDisadvantage: Boolean = false,
    ): Roll {
        var mod = weapon.getToHitModifier()

        // TODO: Ranged weapons
        mod += if(weapon.hasProperty(WeaponProperty.FINESSE)) {
            max(actor.getAbilityModifier(Ability.DEXTERITY), actor.getAbilityModifier(Ability.STRENGTH))
        }
        else {
            actor.getAbilityModifier(Ability.STRENGTH)
        }

        // TODO: Check if character is proficient with weapon
        mod += actor.proficiencyBonus

        return when {
            withAdvantage -> Roll(Die.D20_Advantage, modifier = mod)
            withDisadvantage -> Roll(Die.D20_Disadvantage, modifier = mod)
            else -> Roll(Die.D20, modifier = mod)
        }
    }

}