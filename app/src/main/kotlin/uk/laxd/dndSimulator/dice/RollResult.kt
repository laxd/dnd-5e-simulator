package uk.laxd.dndSimulator.dice

class RollResult {
    private val dieResults = mutableListOf<Pair<Die, Int>>()
    private var modifier = 0

    constructor(dice: List<Die> = emptyList(), modifier: Int = 0) {
        for (die in dice) {
            dieResults.add(Pair(die, die.roll()))
        }

        this.modifier = modifier
    }

    fun addDice(vararg dice: Die) {
        for(die in dice) {
            dieResults.add(Pair(die, die.roll()))
        }
    }

    fun addModifier(modifier: Int) {
        this.modifier += modifier
    }

    override fun toString(): String {
        if (dieResults.isEmpty()) {
            // No dies to roll, just a straight value
            return "$modifier"
        }

        var dieStrings = mutableListOf<String>()

        // Group by die type, i.e. group d20s, d12s etc
        for(type in listOf(Die.D20, Die.D12, Die.D8, Die.D6, Die.D4)) {
            val dice = dieResults.filter { it.first == type }

            if(dice.isEmpty()) {
                continue
            }

            var dieString = dice.count().toString() + type.toString()

            dieString += dice.map { it.second }
                .joinToString(separator = ", ", prefix = " (", postfix = ")")

            dieStrings.add(dieString)
        }

        val modifierString = if (modifier < 0) "- $modifier" else "+ $modifier"

        return "${dieStrings.joinToString(separator = ", ")} $modifierString"
    }

    // Return ONLY the sum of the dice
    val dieOutcome: Int
        get() =// Return ONLY the sum of the dice
            dieResults.map { it.second }.toIntArray().sum()

    // TODO: Add string builder type functionality? Add decorator?
    val outcome: Int
        get() = dieOutcome + modifier
}