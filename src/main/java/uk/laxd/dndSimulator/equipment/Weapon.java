package uk.laxd.dndSimulator.equipment;

import uk.laxd.dndSimulator.action.AttackAction;
import uk.laxd.dndSimulator.action.DamageType;
import uk.laxd.dndSimulator.dice.Die;

import java.util.Collection;
import java.util.Collections;

public abstract class Weapon {

    public abstract int getToHitModifier(AttackAction attackAction);

    public abstract Collection<Die> getDamageDice(AttackAction attackAction);

    /**
     * The additional damage portion of the weapon ONLY.
     *
     * i.e. for a 2d6 + 4 weapon, this should return 4
     * @param attackAction
     * @return
     */
    public abstract int getDamage(AttackAction attackAction);

    public abstract DamageType getDamageType();

    public Collection<WeaponProperty> getProperties() {
        return Collections.emptyList();
    }

    public boolean hasProperty(WeaponProperty weaponProperty) {
        return getProperties().contains(weaponProperty);
    }

    public void onAttack(AttackAction attackAction) {

    }

    public void onHit(AttackAction attackAction) {

    }

}
