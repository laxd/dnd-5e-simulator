package uk.laxd.dndSimulator.action

import uk.laxd.dndSimulator.character.Character

abstract class TargetSelector {
    abstract val target: Character?
}