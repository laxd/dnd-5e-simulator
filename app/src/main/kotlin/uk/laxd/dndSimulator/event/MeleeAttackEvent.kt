package uk.laxd.dndSimulator.event

import uk.laxd.dndSimulator.action.AttackOutcome
import uk.laxd.dndSimulator.action.Damage
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.dice.Roll
import uk.laxd.dndSimulator.dice.RollResult
import uk.laxd.dndSimulator.equipment.Weapon
import uk.laxd.dndSimulator.feature.Effect

class MeleeAttackEvent(
    actor: Character?, // Who
    val weapon: Weapon, // With what
    val target: Character, // To whom
    val attackRoll: RollResult,
    val damageRoll: RollResult,
    val outcome: AttackOutcome, // What happened
    var amount: Damage, // How much
    var effect: Effect? = null //
): EncounterEvent(
    actor,
    EncounterEventType.MELEE_ATTACK,
    when (outcome) {
        AttackOutcome.NOT_PERFORMED -> ""
        AttackOutcome.MISS -> "$actor attacked $target with $weapon but missed!"
        AttackOutcome.HIT -> "$actor attacked $target with $weapon ($attackRoll) dealing $amount ($damageRoll) damage"
        AttackOutcome.CRIT -> "$actor attacked $target with $weapon (nat 20) dealing $amount ($damageRoll) damage)"
    }
)