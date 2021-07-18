package uk.laxd.dndSimulator.equipment

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ArmourTest {
    @Test
    internal fun `light armour has light-armour proficiency type`() {
        val lightArmour = PaddedArmour()

        assertThat(lightArmour.getProficiencyNames())
            .contains("light-armour")
    }

    @Test
    internal fun `medium armour has medium-armour proficiency type`() {
        val lightArmour = HideArmour()

        assertThat(lightArmour.getProficiencyNames())
            .contains("medium-armour")
    }

    @Test
    internal fun `heavy armour has heavy-armour proficiency type`() {
        val lightArmour = SplintArmour()

        assertThat(lightArmour.getProficiencyNames())
            .contains("heavy-armour")
    }
}