package uk.laxd.dndSimulator.feature

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.action.Action
import uk.laxd.dndSimulator.action.AttackAction
import uk.laxd.dndSimulator.action.InitiativeRoll
import uk.laxd.dndSimulator.action.MeleeAttackAction
import uk.laxd.dndSimulator.character.Character
/**
 * Mirrors [Feature] methods to allow a single point for executing features at certain stages
 */
@Component
class FeatureEventProcessor(registry: FeatureRegistry) {

    fun onInitiative(character: Character, initiativeRoll: InitiativeRoll ) {
        character.features.forEach { f: Feature -> f.onInitiativeRoll(initiativeRoll) }
    }

    fun onAttack() {

    }

    fun onDamageRoll(action: AttackAction) {
        // Resolve all pre-damage features
        action.actor.features.forEach { feature: Feature -> feature.onDamageRoll(action) }
        action.target.features.forEach { feature: Feature -> feature.onDamageRollReceived(action) }
    }

    fun onDamageDealt(action: AttackAction) {
        // Resolve all post-damage features;
        action.actor.features.forEach { f: Feature -> f.onDamageInflicted(action) }
        action.target.features.forEach { f: Feature -> f.onDamageReceived(action) }
    }

    fun onAttackRoll(action: AttackAction) {
        action.actor.features.forEach { feature: Feature -> feature.onAttackRoll(action) }
        action.target.features.forEach { feature: Feature -> feature.onAttackRollReceiving(action) }
    }

    fun onCombatStart(characters: Collection<Character>) {
        characters.forEach { c ->
            c.features.forEach { f -> f.onCombatStart(c) }
        }
    }

    fun onTurnStart(character: Character) {
        character.features.forEach { f -> f.onTurnStart(character) }
    }

    fun onTurnEnd(character: Character) {
        character.features.forEach { f -> f.onTurnStart(character) }
    }

}

