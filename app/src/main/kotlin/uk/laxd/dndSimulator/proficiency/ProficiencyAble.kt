package uk.laxd.dndSimulator.proficiency

// TODO: Rename this to something more sensible?
interface ProficiencyAble {

    /**
     * Get the strings that identify the proficiency required in order to
     * be proficient in this Effect.
     *
     * If the character has ANY of the proficiency names returned, the character
     * is considered proficient.
     *
     * If no proficiency is required, this method should return an empty list.
     */
    fun getProficiencyNames(): List<String>
}