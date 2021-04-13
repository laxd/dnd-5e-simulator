package uk.laxd.dndSimulator.feature

/**
 * A `Feature` that is always active, and applies whenever a condition is met, i.e. Brutal Critical
 */
abstract class StaticFeature(name: String) : Feature(name) {
    override val isActiveSkill: Boolean
        get() = false

    override var isActive: Boolean
        get() = true
        set(isActive) {
            super.isActive = isActive
        }
}