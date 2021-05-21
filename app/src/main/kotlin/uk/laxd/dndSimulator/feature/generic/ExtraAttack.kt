package uk.laxd.dndSimulator.feature.generic

import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.feature.StaticFeature
import uk.laxd.dndSimulator.character.CharacterClass

class ExtraAttack : StaticFeature("Extra Attack") {
    override fun onCreate(character: Character) {
        character.attackCount = 2
    }

    // TODO: Ranger/Fighter too
    override val characterClassRequired: CharacterClass = CharacterClass.BARBARIAN
    override val levelRequirement: Int = 5
}