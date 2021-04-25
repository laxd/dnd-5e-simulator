package uk.laxd.dndSimulator.action

enum class AttackOutcome(val isHit: Boolean) {
    HIT(true), MISS(false), CRIT(true), NOT_PERFORMED(false)
}