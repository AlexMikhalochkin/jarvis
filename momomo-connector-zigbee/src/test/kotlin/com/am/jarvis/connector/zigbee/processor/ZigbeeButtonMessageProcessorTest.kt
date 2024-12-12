import com.am.jarvis.connector.zigbee.processor.ZigbeeButtonMessageProcessor
import com.am.jarvis.core.api.MqttMessagePublisher
import com.am.jarvis.core.api.Notifier
import io.mockk.Called
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ZigbeeButtonMessageProcessorTest {

    @MockK(relaxUnitFun = true)
    private lateinit var publisher: MqttMessagePublisher

    @MockK(relaxUnitFun = true)
    private lateinit var notifier: Notifier

    private lateinit var processor: ZigbeeButtonMessageProcessor

    @BeforeEach
    fun setUp() {
        processor = ZigbeeButtonMessageProcessor(publisher, "megad.id/cmd", listOf(notifier))
    }

    @Test
    fun process_doubleAction_publishesCorrectMessage() {
        val message = """{"action": "double"}""".toByteArray()

        processor.process(message)

        verify { publisher.publish("megad.id/cmd", "13:2") }
    }

    @Test
    fun process_singleAction_publishesCorrectMessage() {
        val message = """{"action": "single"}""".toByteArray()

        processor.process(message)

        verify { publisher.publish("megad.id/cmd", "22:2") }
    }

    @Test
    fun process_emptyAction_publishesDefaultMessage() {
        val message = """{"action": ""}""".toByteArray()

        processor.process(message)

        verify { publisher wasNot Called }
    }

    @Test
    fun process_invalidJson_throwsException() {
        val message = """{"invalid": "json"}""".toByteArray()

        assertThrows<Exception> {
            processor.process(message)
        }

        verify { publisher wasNot Called }
    }

    @Test
    fun getSupportedTopics_returnsCorrectTopics() {
        val topics = processor.getSupportedTopics()

        assertEquals(listOf("zigbee2mqtt/button"), topics)
    }
}
