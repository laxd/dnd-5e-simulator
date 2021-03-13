package uk.laxd.dndSimulator.equipment;

import uk.laxd.dndSimulator.ability.Ability;
import uk.laxd.dndSimulator.action.AttackAction;
import uk.laxd.dndSimulator.action.DamageType;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.dice.Die;

import java.util.Collection;
import java.util.Collections;

public class UnarmedAttack extends Weapon {
    @Override
    public int getToHitModifier(AttackAction attackAction) {
        Character attacker = attackAction.getPerformer();
        return attacker.getAbilityModifier(Ability.STRENGTH) + attacker.getProficiencyBonus();
    }

    @Override
    public Collection<Die> getDamageDice(AttackAction attackAction) {
        return Collections.emptyList();
    }

    @Override
    public int getDamage(AttackAction attackAction) {
        return attackAction.getPerformer().getAbilityModifier(Ability.STRENGTH) + 1;
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.BLUDGEONING;
    }
}
