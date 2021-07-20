package uk.laxd.dndSimulator.config.json

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import uk.laxd.dndSimulator.config.PostSimulationEventFactory
import uk.laxd.dndSimulator.equipment.ArmourRegistry
import uk.laxd.dndSimulator.equipment.StuddedLeatherArmour
import uk.laxd.dndSimulator.proficiency.Proficiency
import uk.laxd.dndSimulator.proficiency.ProficiencyType
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonConfigParserIntegrationTest {

    private val armourRegistry: ArmourRegistry = mockk()
    private val postSimulationEventFactory: PostSimulationEventFactory = mockk()

    private val configBuilder: JsonConfigBuilder = JsonConfigBuilder(postSimulationEventFactory)

    private val configParser: JsonConfigParser = JsonConfigParser(configBuilder)

    init {
        every { postSimulationEventFactory.createEvent(any()) }.returns(mockk())
        every { armourRegistry.getArmour("Studded leather armour") }.returns(StuddedLeatherArmour())
    }

    @Test
    internal fun `config includes simulation count`() {
        val simulationConfig = configParser.getConfig("src/test/resources/test.json")

        assertEquals(10000, simulationConfig.simulationCount)
    }

    @Test
    internal fun `config includes characters`() {
        val simulationConfig = configParser.getConfig("src/test/resources/test.json")

        assertThat(simulationConfig.characterConfigs).hasSize(3)
    }

    @Test
    internal fun `config includes post simulation events`() {
        val simulationConfig = configParser.getConfig("src/test/resources/test.json")

        assertThat(simulationConfig.postEvents).hasSize(2)
    }

    @Test
    internal fun `config includes characters weapons`() {
        val simulationConfig = configParser.getConfig("src/test/resources/test.json")

        assertThat(simulationConfig.characterConfigs.find { c -> c.name == "Magnus" }?.inventory)
            .extracting("name")
            .contains("Greataxe", "Shortbow")
    }

    @Test
    internal fun `config includes armour with lookup type`() {
        val simulationConfig = configParser.getConfig("src/test/resources/test.json")

        assertThat(simulationConfig.characterConfigs.find { c -> c.name == "Magnus" }?.inventory)
            .extracting("name")
            .contains("Studded leather armour")
    }

    @Test
    internal fun `config includes armour with custom type`() {
        val simulationConfig = configParser.getConfig("src/test/resources/test.json")

        assertThat(simulationConfig.characterConfigs.find { c -> c.name == "Magnus" }?.inventory)
            .extracting("name")
            .contains("My super awesome armour")
    }

    @Test
    internal fun `config with manual AC is set`() {
        val simulationConfig = configParser.getConfig("src/test/resources/test.json")

        assertThat(simulationConfig.characterConfigs.find { c -> c.name == "Magnus" }?.overrideArmourClass)
            .isEqualTo(18)
    }

    @Test
    internal fun `config with proficiencies set contains those proficiencies`() {
        val simulationConfig = configParser.getConfig("src/test/resources/test.json")

        assertThat(simulationConfig.characterConfigs.find { c -> c.name == "Magnus" }?.proficiencies)
            .extracting("name")
            .contains("Charisma saving throws", "Simple weapons")
    }
}