package uk.laxd.dndSimulator.config

import org.springframework.stereotype.Component
import uk.laxd.dndSimulator.config.internal.CustomWeaponConfig
import uk.laxd.dndSimulator.config.internal.LookupWeaponConfig
import uk.laxd.dndSimulator.config.internal.WeaponConfig
import uk.laxd.dndSimulator.equipment.CustomWeapon
import uk.laxd.dndSimulator.equipment.Dagger
import uk.laxd.dndSimulator.equipment.Weapon
import java.lang.IllegalArgumentException

@Component
class WeaponFactory {

    fun createWeapon(weaponConfig: WeaponConfig): Weapon {
        return when(weaponConfig) {
            is LookupWeaponConfig -> Dagger()
            is CustomWeaponConfig -> CustomWeapon(
                weaponConfig.name,
                weaponConfig.damageType,
                weaponConfig.diceDamage,
                weaponConfig.diceCount,
                weaponConfig.damageBonus,
                weaponConfig.attackBonus,
                weaponConfig.properties,
                weaponConfig.range,
                weaponConfig.priority
            )
            else -> throw IllegalArgumentException("Unknown type of WeaponConfig: $weaponConfig")
        }
    }

}