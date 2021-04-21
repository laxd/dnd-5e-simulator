package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.character.Character

interface TargetSelector {

    fun findTarget(actor: Character): Character?

}