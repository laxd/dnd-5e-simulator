package uk.laxd.dndSimulator.config.json

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import uk.laxd.dndSimulator.config.PostSimulationEventFactory
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonConfigParserIntegrationTest {

    private val postSimulationEventFactory: PostSimulationEventFactory = mockk()

    private val configBuilder: JsonConfigBuilder = JsonConfigBuilder(postSimulationEventFactory)

    private val configParser: JsonConfigParser = JsonConfigParser(configBuilder)

    init {
        every { postSimulationEventFactory.createEvent(any()) }.returns(mockk())
    }

    @Test
    internal fun `config includes simulation count`() {
        val simulationConfig = configParser.getConfig("src/test/resources/test.json")

        assertEquals(10000, simulationConfig.simulationCount)
    }

    @Test
    internal fun `config includes characters`() {
        val simulationConfig = configParser.getConfig("src/test/resources/test.json")

        assertThat(simulationConfig.characterConfigs).hasSize(2)
    }

    @Test
    internal fun `config includes post simulation events`() {
        val simulationConfig = configParser.getConfig("src/test/resources/test.json")

        assertThat(simulationConfig.postEvents).hasSize(2)
    }
}