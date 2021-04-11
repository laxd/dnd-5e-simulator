package uk.laxd.dndSimulator.feature;

import org.springframework.stereotype.Component;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.character.CharacterConfig;
import uk.laxd.dndSimulator.feature.barbarian.Rage;
import uk.laxd.dndSimulator.feature.barbarian.RecklessAttack;
import uk.laxd.dndSimulator.feature.barbarian.UnarmoredDefence;
import uk.laxd.dndSimulator.feature.rogue.SneakAttack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FeatureFactory {

    private final Collection<Class<? extends Feature>> features = new ArrayList<>();

    // TODO: Load all features via reflection instead?
    {
        features.add(Rage.class);
        features.add(RecklessAttack.class);
        features.add(UnarmoredDefence.class);
        features.add(SneakAttack.class);
    }

    public Collection<Feature> createFeatures(CharacterConfig config) {
        // TODO: Also populate manually added features via CharacterConfig
        return features.stream()
                .map(f -> {
                    try {
                        return f.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .filter(f -> config.getCharacterClasses().contains(f.getCharacterClassRequired()))
                .filter(f -> f.getLevelRequirement() <= config.getLevel(f.getCharacterClassRequired()))
                .collect(Collectors.toList());
    }

}
