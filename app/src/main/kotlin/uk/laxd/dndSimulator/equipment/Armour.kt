package uk.laxd.dndSimulator.equipment

import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.character.Character
import kotlin.math.max
import kotlin.math.min

/**
 * Any sort of wearable armour that provides some sort of
 * protection to the wearer (including Shields)
 */
abstract class Armour(name: String) : Equipment(name) {

    /**
     * Returns the static armour class provided by this armour.
     *
     * Does NOT include any bonus to AC provided by the [Character]'s dexterity
     *
     * If this armour does NOT set AC directly (i.e. only provides a bonus), this
     * should be set to null
     */
    abstract val armourClass: Int?

    /**
     * Returns any additional AC, either due to dexterity provided by this armour,
     * or just straight bonus AC if this armour does not provide base AC.
     *
     * For shields, this just returns the additional AC provided by the shield.
     */
    abstract fun getAdditionalArmourClass(character: Character): Int

    /**
     * Minimum strength required to wear this armour without imposing
     * a 10 foot movement penalty
     */
    abstract val requiredStrength: Int

    /**
     * Whether this armour implies disadvantage on stealth to the wearer
     */
    abstract val disadvantageOnStealth: Boolean

    /**
     * Category of this armour
     */
    abstract val armourCategory: ArmourCategory

}

class PaddedArmour : Armour("Padded armour") {
    override val armourClass = 11
    override val requiredStrength = 0
    override val disadvantageOnStealth = true
    override val armourCategory = ArmourCategory.LIGHT

    override fun getAdditionalArmourClass(character: Character) = character.getAbilityModifier(Ability.DEXTERITY)
}

class LeatherArmour : Armour("Leather armour") {
    override val armourClass = 11
    override val requiredStrength = 0
    override val disadvantageOnStealth = false
    override val armourCategory = ArmourCategory.LIGHT

    override fun getAdditionalArmourClass(character: Character) = character.getAbilityModifier(Ability.DEXTERITY)
}

class StuddedLeatherArmour : Armour("Studded leather armour") {
    override val armourClass = 12
    override val requiredStrength = 0
    override val disadvantageOnStealth = false
    override val armourCategory = ArmourCategory.LIGHT

    override fun getAdditionalArmourClass(character: Character) = character.getAbilityModifier(Ability.DEXTERITY)
}

class HideArmour : Armour("Hide armour") {
    override val armourClass = 12
    override val requiredStrength = 0
    override val disadvantageOnStealth = false
    override val armourCategory = ArmourCategory.MEDIUM

    override fun getAdditionalArmourClass(character: Character) = min(character.getAbilityModifier(Ability.DEXTERITY), 2)
}

class ChainShirtArmour : Armour("Chain shirt armour") {
    override val armourClass = 13
    override val requiredStrength = 0
    override val disadvantageOnStealth = false
    override val armourCategory = ArmourCategory.MEDIUM

    override fun getAdditionalArmourClass(character: Character) = min(character.getAbilityModifier(Ability.DEXTERITY), 2)
}

class ScaleMailArmour : Armour("Scale mail armour") {
    override val armourClass = 14
    override val requiredStrength = 0
    override val disadvantageOnStealth = true
    override val armourCategory = ArmourCategory.MEDIUM

    override fun getAdditionalArmourClass(character: Character) = min(character.getAbilityModifier(Ability.DEXTERITY), 2)
}

class BreastplateArmour : Armour("Breastplate armour") {
    override val armourClass = 14
    override val requiredStrength = 0
    override val disadvantageOnStealth = false
    override val armourCategory = ArmourCategory.MEDIUM

    override fun getAdditionalArmourClass(character: Character) = min(character.getAbilityModifier(Ability.DEXTERITY), 2)
}

class HalfPlateArmour : Armour("Half plate armour") {
    override val armourClass = 15
    override val requiredStrength = 0
    override val disadvantageOnStealth = true
    override val armourCategory = ArmourCategory.MEDIUM

    override fun getAdditionalArmourClass(character: Character) = min(character.getAbilityModifier(Ability.DEXTERITY), 2)
}

class RingMailArmour : Armour("Ring mail armour") {
    override val armourClass = 14
    override val requiredStrength = 0
    override val disadvantageOnStealth = true
    override val armourCategory = ArmourCategory.HEAVY

    override fun getAdditionalArmourClass(character: Character) = 0
}

class ChainMailArmour : Armour("Chain mail armour") {
    override val armourClass = 16
    override val requiredStrength = 13
    override val disadvantageOnStealth = true
    override val armourCategory = ArmourCategory.HEAVY

    override fun getAdditionalArmourClass(character: Character) = 0
}

class SplintArmour : Armour("Splint armour") {
    override val armourClass = 17
    override val requiredStrength = 15
    override val disadvantageOnStealth = true
    override val armourCategory = ArmourCategory.HEAVY

    override fun getAdditionalArmourClass(character: Character) = 0
}

class PlateArmour : Armour("Plate armour") {
    override val armourClass = 18
    override val requiredStrength = 15
    override val disadvantageOnStealth = true
    override val armourCategory = ArmourCategory.HEAVY

    override fun getAdditionalArmourClass(character: Character) = 0
}

class Shield : Armour("Shield") {
    override val armourClass: Int? = null
    override val requiredStrength = 0
    override val disadvantageOnStealth = false
    override val armourCategory = ArmourCategory.SHIELD

    override fun getAdditionalArmourClass(character: Character) = 2
}

/**
 * Allows armour to be extended with bonuses (i.e. creating a +1 studded leather armour).
 *
 * In particular, when creating armour from some sort of text representation, this allows creating
 * armour based on another type of armour:
 *
 * {
 *     name: "Super awesome homebrew studded leather armour",
 *     bonus: 2,
 *     armour: "Studded Leather Armour"
 * }
 */
class ExtendedArmour(
    name: String,
    val bonus: Int,
    val armour: Armour
) : Armour(name) {
    override val armourClass = armour.armourClass
    override fun getAdditionalArmourClass(character: Character) = armour.getAdditionalArmourClass(character) + bonus

    override val requiredStrength = armour.requiredStrength
    override val disadvantageOnStealth = armour.disadvantageOnStealth
    override val armourCategory = armour.armourCategory
}

/**
 * Allows implementations to implement their own custom armour
 */
class CustomArmour(
    name: String,
    override val armourClass: Int,
    val hasDexBonus: Boolean,
    val maxDexBonus: Int? = null,
    override val requiredStrength: Int = 0,
    override val disadvantageOnStealth: Boolean = false,
    override val armourCategory: ArmourCategory
): Armour(name) {
    override fun getAdditionalArmourClass(character: Character): Int {
        if(!hasDexBonus) {
            return 0
        }

        return min(maxDexBonus ?: 100, character.getAbilityModifier(Ability.DEXTERITY))
    }
}