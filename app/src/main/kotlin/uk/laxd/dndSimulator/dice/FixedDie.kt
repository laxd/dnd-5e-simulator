package uk.laxd.dndSimulator.dice

class FixedDie(private val value: Int, maxValue: Int) : Die(maxValue) {
    override fun roll(): Int {
        return this.value
    }
}