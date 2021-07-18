package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.character.Character

/**
 * Any single use item that a character can use as an action such as:
 *
 * * Potions
 * * Scrolls
 * * Any other usable trinkets
 */
abstract class Consumable(name: String) : Item(name) {

    val uses: Int = 1

    abstract val category: ConsumableCategory

    abstract fun onUse(user: Character, target: Character)
}

enum class ConsumableCategory {
    POTION,
    SCROLL,
    WONDROUS_ITEM
}