package uk.laxd.dndSimulator.action

import org.slf4j.LoggerFactory
import uk.laxd.dndSimulator.event.EncounterEventType
import java.util.HashMap
import java.util.stream.Collectors
import uk.laxd.dndSimulator.character.Character
import uk.laxd.dndSimulator.event.EventLogger
import uk.laxd.dndSimulator.feature.Feature
import java.util.function.Consumer

class Encounter(
    private val turnFactory: TurnFactory,
    private val eventLogger: EventLogger,
    private val participants: Collection<Character>
) {

    fun startEncounter() {
        LOGGER.debug("Starting encounter")
        eventLogger.logEvent(EncounterEventType.ENCOUNTER_START)

        // Create a list of turns, sorted by initiative
        val charactersByInitiative: MutableMap<Character, Int> = HashMap()
        for (character in participants) {
            val initiativeRoll = InitiativeRoll()
            character.features.forEach(Consumer { f: Feature -> f.onInitiativeRoll(initiativeRoll) })
            charactersByInitiative[character] = initiativeRoll.roll().outcome + character.initiativeModifier
        }

        val characters = charactersByInitiative.keys

        // TODO: Change this once multiple characters allowed
        while (participants.stream().allMatch { p: Character? -> p!!.hp > 0 }) {
            eventLogger.logEvent(EncounterEventType.ROUND_START)
            for (character in characters) {

                // TODO: Remove instantiation here, move to factory?
                // Should a character be able to contain its own target selector?
                val turn = turnFactory.createTurn(character, SimpleTargetSelector(getTarget(character)!!))
                turn.doTurn()

                // If anyone is dead, end the encounter
                if (participants.stream().anyMatch { p: Character? -> p!!.hp == 0 }) {
                    break
                }
            }
        }
        LOGGER.debug("Finishing encounter")
    }

    // TODO: Choose target based on team.
    // TODO: Allow onTargetSelect method on feature to allow e.g. cursed items to target nearest etc.
    private fun getTarget(forCharacter: Character): Character? {
        return participants.stream()
            .filter { c: Character? -> c !== forCharacter }
            .findFirst()
            .orElse(null)
    }

    val LOGGER = LoggerFactory.getLogger(Encounter::class.java)
}