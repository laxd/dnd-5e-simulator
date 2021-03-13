package uk.laxd.dndSimulator.action;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Defines the damage caused by an attack, which
 * may consist of many different types
 */
public class Damage {

    private Map<DamageType, Integer> damageMap = new HashMap<>();

    public Map<DamageType, Integer> getDamageMap() {
        return damageMap;
    }

    public int getAmount(DamageType damageType) {
        return damageMap.getOrDefault(damageType, 0);
    }

    public void addAmount(DamageType damageType, int amount) {
        if(amount == 0) {
            return;
        }

        int currentAmount = getAmount(damageType);
        damageMap.put(damageType, currentAmount + amount);
    }

    @Override
    public String toString() {
        return damageMap.entrySet().stream()
                .map(e -> (e.getValue().toString() + " " + e.getKey().toString()))
                .collect(Collectors.joining(", "));
    }
}
