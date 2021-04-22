package uk.laxd.dndSimulator.action

enum class DamageType(val display: String) {
    ACID("Acid"),
    BLUDGEONING("Bludgeoning"),
    COLD("Cold"),
    FIRE("Fire"),
    FORCE("Force"),
    LIGHTNING("Lightning"),
    NECROTIC("Necrotic"),
    PIERCING("Piercing"),
    POISON("Poison"),
    PSYCHIC("Psychic"),
    RADIANT("Radiant"),
    SLASHING("Slashing"),
    THUNDER("Thunder");

    override fun toString() = display
}