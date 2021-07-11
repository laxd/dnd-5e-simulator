package uk.laxd.dndSimulator.proficiency

/**
 * A proficiency is defined by both a [name] and a [type].
 *
 * i.e. Proficiency in Wisdom saving throws would be defined as follows:
 *
 * name = Wisdom saving throws
 * type = Saving Throw
 *
 * Proficiency in simple weapons:
 *
 * name = Simple weapons
 * type = Weapons
 *
 * Proficiency in thieves tools:
 *
 * name = Thieves tools
 * type = Tools
 *
 * Proficiency in Stealth:
 *
 * name = Stealth
 * type = Ability
 */
class Proficiency(
    val name: String,
    val type: ProficiencyType
)

/**
 * All of the different types of proficiencies that are given to a Character.
 *
 * This list does NOT include:
 *
 * * Proficiency bonus added to spell casts (all spells benefit from this)
 * * Saving throw DC calculation from spell casts
 * * Specific instances of proficiency being added, i.e.
 *
 */
enum class ProficiencyType {
    SAVING_THROWS,
    WEAPONS,
    ARMOUR,
    TOOLS,
    ABILITIES
}