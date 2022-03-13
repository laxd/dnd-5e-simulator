package uk.laxd.dndSimulator.action

import org.slf4j.LoggerFactory
import uk.laxd.dndSimulator.action.initiative.InitiativeProvider
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.event.*

class Encounter(
    private val turnFactory: TurnFactory,
    private val participants: Collection<Character>,
    private val targetSelector: TargetSelector,
    private val initiativeProvider: InitiativeProvider
) {

    fun startEncounter() {
        LOGGER.debug("Starting encounter")
        EventLogger.instance.logEvent(EncounterStartEvent())

        // Create a list of characters, sorted by initiative
        val characters = participants.sortedBy { initiativeProvider.getInitiative(it) }

        characters.forEach { c -> c.onCombatStart() }

        while (!isEncounterFinished()) {
            EventLogger.instance.logEvent(RoundStartEvent())
            for (character in characters) {
                if(character.hp > 0) {
                    // TODO: Notify ALL characters about a character's turn start
                    character.onTurnStart()

                    turnFactory.createTurn(character, targetSelector)
                        .doTurn()

                    character.onTurnEnd()
                }
            }
        }

        EventLogger.instance.logEvent(EncounterFinishedEvent(
            participants.filter { c -> c.hp > 0 }
                .map { c -> c.team }
                .first()
        ))

        LOGGER.debug("Finishing encounter")
    }

    /**
     * The encounter is finished if there is only a single team remaining.
     */
    fun isEncounterFinished(): Boolean {
        return participants.filter { p -> p.hp > 0 }
            .map { p -> p.team }
            .distinct()
            .count() <= 1
    }

    val LOGGER = LoggerFactory.getLogger(Encounter::class.java)
}