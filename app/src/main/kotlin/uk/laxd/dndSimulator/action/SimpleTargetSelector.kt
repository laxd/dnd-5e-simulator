package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.character.Character

class SimpleTargetSelector(
    private val characters: Collection<Character>
) : TargetSelector {
    override fun findTarget(actor: Character): Character? {
        if (actor.team == null) {
            // Doesn't belong to a team, choose a random target
            return characters.filter { c -> c != actor }.randomOrNull()
        }

        // Find someone on a team that isn't the Characters team!
        return characters.filter { c -> c.hp > 0 }
            .firstOrNull { c -> c.team != actor.team }
    }
}