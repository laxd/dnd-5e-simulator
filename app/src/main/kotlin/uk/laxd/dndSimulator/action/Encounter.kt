package uk.laxd.dndSimulator.action

import org.slf4j.LoggerFactory
import java.util.HashMap
import java.util.stream.Collectors
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.event.*
import uk.laxd.dndSimulator.feature.Feature
import uk.laxd.dndSimulator.feature.FeatureEventProcessor
import java.util.function.Consumer

class Encounter(
    private val turnFactory: TurnFactory,
    private val eventLogger: EventLogger,
    private val participants: Collection<Character>,
    private val targetSelector: TargetSelector,
    private val featureEventProcessor: FeatureEventProcessor
) {

    fun startEncounter() {
        LOGGER.debug("Starting encounter")
        eventLogger.logEvent(EncounterStartEvent())

        // Create a list of characters, sorted by initiative
        val characters = participants.sortedBy { getInitiative(it) }

        featureEventProcessor.onCombatStart(characters)

        while (!isEncounterFinished()) {
            eventLogger.logEvent(RoundStartEvent())
            for (character in characters) {
                if(character.hp > 0) {
                    featureEventProcessor.onTurnStart(character)

                    turnFactory.createTurn(character, targetSelector)
                        .doTurn()

                    featureEventProcessor.onTurnEnd(character)
                }
            }
        }

        eventLogger.logEvent(EncounterFinishedEvent(
            participants.filter { c -> c.hp > 0 }
                .map { c -> c.team }
                .first()
        ))

        LOGGER.debug("Finishing encounter")
    }

    private fun getInitiative(character: Character): Int {
        val roll = InitiativeRoll()

        featureEventProcessor.onInitiative(character, roll)

        return roll.roll().outcome + character.initiativeModifier
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