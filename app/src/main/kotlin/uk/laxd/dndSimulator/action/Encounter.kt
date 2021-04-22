package uk.laxd.dndSimulator.action

import org.slf4j.LoggerFactory
import java.util.HashMap
import java.util.stream.Collectors
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.event.*
import uk.laxd.dndSimulator.feature.Feature
import java.util.function.Consumer

class Encounter(
    private val turnFactory: TurnFactory,
    private val eventLogger: EventLogger,
    private val participants: Collection<Character>,
    private val targetSelector: TargetSelector
) {

    fun startEncounter() {
        LOGGER.debug("Starting encounter")
        eventLogger.logEvent(EncounterStartEvent())

        // Create a list of turns, sorted by initiative
        val charactersByInitiative: MutableMap<Character, Int> = HashMap()
        for (character in participants) {
            val initiativeRoll = InitiativeRoll()
            character.features.forEach(Consumer { f: Feature -> f.onInitiativeRoll(initiativeRoll) })
            charactersByInitiative[character] = initiativeRoll.roll().outcome + character.initiativeModifier
        }

        val characters = charactersByInitiative.keys

        // TODO: Change this once multiple characters allowed
        while (!isEncounterFinished()) {
            eventLogger.logEvent(RoundStartEvent())
            for (character in characters) {
                if(character.hp > 0) {
                    turnFactory.createTurn(character, targetSelector)
                        .doTurn()
                }
            }
        }
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