package uk.laxd.dndSimulator.feature

import uk.laxd.dndSimulator.character.CharacterClass

/**
 * A `Feature` is a specific type of [Effect] that
 * a character obtains through their class, race or
 */
abstract class Feature(name: String) : Effect(name) {
    open var isActive = false

    open fun activate() {
        isActive = true
    }

    fun deactivate() {
        isActive = false
    }

    open val levelRequirement: Int
        get() = 1

    abstract val isActiveSkill: Boolean
    abstract val characterClassRequired: CharacterClass

    // TODO: onInitiativeRoll
    // TODO: onTurnStart/onTurnEnd for other characters, i.e. flaming orb, if an enemy ends their turn, damage them
    open fun reset() {}
}