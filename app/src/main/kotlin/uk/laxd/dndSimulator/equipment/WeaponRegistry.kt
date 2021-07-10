package uk.laxd.dndSimulator.equipment

import org.springframework.stereotype.Component
import kotlin.reflect.KClass

/**
 * A registry for weapons. Pre-created weapons can be added here
 * to be made available for use when creating a character with pre-built weapons
 */
interface WeaponRegistry {
    fun getWeapon(name: String): Weapon?
    fun register(weapon: KClass<out Weapon>)
}

@Component
class SimpleWeaponRegistry: WeaponRegistry {

    private val weaponClasses = mutableListOf<KClass<out Weapon>>()

    override fun getWeapon(name: String): Weapon? {
        return weaponClasses.map { c -> c.constructors.first().call() }.find { a -> a.name == name }
    }

    override fun register(weapon: KClass<out Weapon>) {
        this.weaponClasses.add(weapon)
    }

}