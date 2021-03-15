package uk.laxd.dndSimulator.action;

import uk.laxd.dndSimulator.character.Character;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EncounterOutcome {

    private int rounds = 0;
    private Collection<Character> participants = new ArrayList<>();
    private Map<Character, CharacterEncounterOutcome> characterOutcomes = new HashMap<>();

    public void addParticipant(Character character) {
        this.participants.add(character);

        this.characterOutcomes.put(character, new CharacterEncounterOutcome(character));
    }

    public CharacterEncounterOutcome getCharacterEncounterOutcome(Character character) {
        return this.characterOutcomes.get(character);
    }

    public Collection<CharacterEncounterOutcome> getCharacterEncounterOutcomes() {
        return characterOutcomes.values();
    }

}
