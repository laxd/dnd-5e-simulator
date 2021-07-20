package uk.laxd.dndSimulator.config.internal

interface ConfigBuilder<T> {
    fun toConfig(): T
}