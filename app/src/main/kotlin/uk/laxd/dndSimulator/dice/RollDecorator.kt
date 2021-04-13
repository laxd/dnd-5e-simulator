package uk.laxd.dndSimulator.dice

class RollDecorator(private val roll: Roll) : Roll() {
    override fun roll(): RollResult {
        return roll.roll()
    }
}