package uk.laxd.dndSimulator.equipment

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.config.internal.ItemType
import kotlin.reflect.KClass

/**
 * A registry for items. Pre-created items can be added here
 * to be made available for use when creating a character with pre-built items
 */
interface ItemRegistry {
    fun getItem(name: String, type: ItemType): Item?
    fun register(item: KClass<out Item>)
}

@Component
class SimpleItemRegistry: ItemRegistry {

    private val itemClasses = mutableListOf<KClass<out Item>>()

    override fun getItem(name: String, type: ItemType): Item? {
        return itemClasses.map { c -> c.constructors.first().call() }.find { a -> a.name == name }
    }

    override fun register(item: KClass<out Item>) {
        this.itemClasses.add(item)
    }

}