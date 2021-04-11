package uk.laxd.dndSimulator.equipment;

import uk.laxd.dndSimulator.ability.Ability;
import uk.laxd.dndSimulator.action.MeleeAttackAction;
import uk.laxd.dndSimulator.action.DamageType;
import uk.laxd.dndSimulator.dice.Die;

import java.util.Arrays;
import java.util.Collection;

public class Greatsword extends Weapon {
    @Override
    public int getToHitModifier(MeleeAttackAction attackAction) {
        return attackAction.getPerformer().getAbilityModifier(Ability.STRENGTH) + attackAction.getPerformer().getProficiencyBonus() + 1;
    }

    @Override
    public Collection<Die> getDamageDice(MeleeAttackAction attackAction) {
        return Arrays.asList(
                Die.D6,
                Die.D6
        );
    }

    @Override
    public int getDamage(MeleeAttackAction attackAction) {
        return attackAction.getPerformer().getAbilityModifier(Ability.STRENGTH);
    }

    @Override
    public Collection<WeaponProperty> getProperties() {
        return Arrays.asList(
                WeaponProperty.HEAVY
        );
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.SLASHING;
    }
}
