package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.character.Character

class SimpleTargetSelector(
    private val characters: Collection<Character>
) : TargetSelector {
    override fun findTarget(actor: Character): Character? {
        // Find someone on a team that isn't the Characters team!
        return characters.filter { c -> c.hp > 0 }
            .firstOrNull { c -> c.team != actor.team }
    }
}