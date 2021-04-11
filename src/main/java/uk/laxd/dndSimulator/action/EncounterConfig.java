package uk.laxd.dndSimulator.action;

import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.character.CharacterConfig;

import java.util.Collection;

public class EncounterConfig {

    private Collection<CharacterConfig> characterConfigs;

    public EncounterConfig(Collection<CharacterConfig> characterConfigs) {
        this.characterConfigs = characterConfigs;
    }

    public Collection<CharacterConfig> getCharacterConfigs() {
        return characterConfigs;
    }
}
