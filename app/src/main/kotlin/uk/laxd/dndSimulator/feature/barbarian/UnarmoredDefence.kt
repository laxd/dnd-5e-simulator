package uk.laxd.dndSimulator.feature.barbarian

import uk.laxd.dndSimulator.feature.StaticFeature
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.event.EncounterEvent

class UnarmoredDefence : StaticFeature("Unarmored Defence") {
    // TODO: OnArmourEquip/OnArmourUnequip
    override fun onCreate(character: Character) {
        // Set AC if not wearing armour
        // TODO: if (!character.getEquippedItems().isWearingArmour())
        val ac = 10 + character.getAbilityModifier(Ability.DEXTERITY) + character.getAbilityModifier(Ability.CONSTITUTION)

        // Override the calculated AC with the one from UnarmouredDefense
        // TODO: This doesn't stack with shields!
        character.overrideArmourClass = ac
    }

    override val characterClassRequired: CharacterClass
        get() = CharacterClass.BARBARIAN
}