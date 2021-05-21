package uk.laxd.dndSimulator.dice

class RollResult {
    private val dieResults = mutableListOf<Pair<Die, Int>>()
    private var modifier = 0

    // TODO: Include a way to add dice with a pre-determined roll? i.e. for skills that
    // set the outcome to full amount
    fun addDice(vararg dice: Die) {
        for(die in dice) {
            dieResults.add(Pair(die, die.roll()))
        }
    }

    fun addModifier(modifier: Int) {
        this.modifier += modifier
    }

    override fun toString(): String {
        val dieString = dieResults.groupingBy { it.first }
            .eachCount()
            .map { e -> e.value.toString() + e.key }
            .joinToString()

        val dieResultString = dieResults.map { it.second }.joinToString()

        return "$dieString [$outcome] ($dieResultString" + if (modifier > 0) " + $modifier)" else ")"
    }

    // Return ONLY the sum of the dice
    val dieOutcome: Int
        get() =// Return ONLY the sum of the dice
            dieResults.map { it.second }.toIntArray().sum()

    // TODO: Add string builder type functionality? Add decorator?
    val outcome: Int
        get() = dieOutcome + modifier
}