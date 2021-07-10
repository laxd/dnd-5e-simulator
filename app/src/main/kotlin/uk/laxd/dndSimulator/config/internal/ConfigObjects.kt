package uk.laxd.dndSimulator.config.internal

interface SimulationConfigBuilder {
    fun toSimulationConfig(): SimulationConfig
}

interface CharacterConfigBuilder {
    fun toCharacterConfig(): CharacterConfig
}

interface WeaponConfigBuilder {
    fun toWeaponConfig(): WeaponConfig
}

interface ArmourConfigBuilder {
    fun toArmourConfig(): ArmourConfig
}