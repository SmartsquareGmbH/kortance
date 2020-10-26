package de.smartsquare.kortance.scenarios.stress

import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
import de.smartsquare.kortance.delay
import de.smartsquare.kortance.randomPayload

class StressClient(
    private val client: Mqtt3BlockingClient,
    private val messages: Int,
    private val wave: Int,
    private val id: Int
) {

    suspend fun run() {
        client.connect()

        repeat(messages) {
            client.publishWith().topic("internal/kortance/$wave/$id").payload(randomPayload(size = 150)).send()

            delay(min = 1, max = 100)
        }

        client.disconnect()
    }
}
