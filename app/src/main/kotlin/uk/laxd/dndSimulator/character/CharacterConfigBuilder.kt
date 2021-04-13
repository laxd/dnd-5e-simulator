package uk.laxd.dndSimulator.character

import uk.laxd.dndSimulator.ability.Ability
import uk.laxd.dndSimulator.equipment.Weapon

class CharacterConfigBuilder private constructor() {

    private var characterConfig: CharacterConfig = CharacterConfig()

    fun withLevels(level: Int, characterClass: CharacterClass): CharacterConfigBuilder {
        characterConfig.addLevel(level, characterClass)
        return this
    }

    fun withArmourClass(ac: Short): CharacterConfigBuilder {
        characterConfig.armourClass = ac
        return this
    }

    fun withAbilityScores(str: Int, dex: Int, con: Int, intelligence: Int, wis: Int, cha: Int): CharacterConfigBuilder {
        characterConfig.abilityScores[Ability.STRENGTH] = str
        characterConfig.abilityScores[Ability.DEXTERITY] = dex
        characterConfig.abilityScores[Ability.CONSTITUTION] = con
        characterConfig.abilityScores[Ability.INTELLIGENCE] = intelligence
        characterConfig.abilityScores[Ability.WISDOM] = wis
        characterConfig.abilityScores[Ability.CHARISMA] = cha
        return this
    }

    fun withAbilityScore(ability: Ability, score: Int): CharacterConfigBuilder {
        characterConfig.abilityScores[ability] = score
        return this
    }

    fun withHp(hp: Short): CharacterConfigBuilder {
        characterConfig.hp = hp
        return this
    }

    // TODO: Change to weapon lookups by ID/Name? Custom weapon definitions?
    fun withWeapon(weapon: Weapon): CharacterConfigBuilder {
        characterConfig.weapon = weapon;
        return this
    }

    fun build(): CharacterConfig {
        return characterConfig
    }

    companion object {
        fun newCharacter(name: String): CharacterConfigBuilder {
            val builder = CharacterConfigBuilder()

            builder.characterConfig.apply {
                this.name = name

                // Set all abilities to 10 by default
                abilityScores[Ability.STRENGTH] = 10
                abilityScores[Ability.DEXTERITY] = 10
                abilityScores[Ability.CONSTITUTION] = 10
                abilityScores[Ability.INTELLIGENCE] = 10
                abilityScores[Ability.WISDOM] = 10
                abilityScores[Ability.CHARISMA] = 10
            }

            return builder
        }
    }
}