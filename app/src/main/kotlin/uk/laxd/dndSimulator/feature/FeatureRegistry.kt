package uk.laxd.dndSimulator.feature

import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class FeatureRegistry {

    val features = mutableListOf<KClass<out Feature>>()

    fun register(clazz: KClass<out Feature>) {
        features.add(clazz)
    }

}