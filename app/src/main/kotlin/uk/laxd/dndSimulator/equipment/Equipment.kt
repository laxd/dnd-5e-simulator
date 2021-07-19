package uk.laxd.dndSimulator.equipment

/**
 * An item that can be equipped, i.e. weapon, armour or a shield
 */
open class Equipment(name: String) : Item(name) {

    var isEquipped: Boolean = false

}