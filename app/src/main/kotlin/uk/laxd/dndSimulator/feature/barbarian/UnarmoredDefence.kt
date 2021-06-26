package uk.laxd.dndSimulator.feature.barbarian

import uk.laxd.dndSimulator.feature.StaticFeature
import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.character.CharacterClass
import uk.laxd.dndSimulator.event.EncounterEvent

class UnarmoredDefence : StaticFeature("Unarmored Defence") {
    override fun onCreate(character: Character) {
        // Set AC if not wearing armour
        // TODO: if (!character.getEquippedItems().isWearingArmour())
        val ac = 10 + character.getAbilityModifier(Ability.DEXTERITY) + character.getAbilityModifier(Ability.CONSTITUTION)
        character.armorClass = ac
    }

    override val characterClassRequired: CharacterClass
        get() = CharacterClass.BARBARIAN
}