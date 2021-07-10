package uk.laxd.dndSimulator.equipment

import org.springframework.stereotype.Component
import kotlin.reflect.KClass

/**
 * A registry for armour. Pre-created pieces of armour can be added here
 * to be made available for use when creating a character with pre-built armour
 */
interface ArmourRegistry {
    fun getArmour(name: String): Armour?
    fun register(armour: KClass<out Armour>)
}

@Component
class SimpleArmourRegistry: ArmourRegistry {

    private val armourClasses = mutableListOf<KClass<out Armour>>()

    override fun getArmour(name: String): Armour? {
        return armourClasses.map { c -> c.constructors.first().call() }.find { a -> a.name == name }
    }

    override fun register(armour: KClass<out Armour>) {
        this.armourClasses.add(armour)
    }

}