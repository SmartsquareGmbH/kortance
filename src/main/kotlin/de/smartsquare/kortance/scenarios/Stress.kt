package de.smartsquare.kortance.scenarios

import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
import de.smartsquare.kortance.ClientFactory
import de.smartsquare.kortance.MqttCommand
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random

class Stress : MqttCommand("Spawns a wave of clients to test the broker autoscaling.") {

    private val waves: Int by option("-w", "--waves", help = "The number of waves.")
        .int().default(10)

    private val jobs: Int by option("-j", "--jobs", help = "The amount of jobs per wave.")
        .int().default(10)

    private val messageCount: Int by option("-m", "--messages", help = "The amount of messages published per job.")
        .int().default(1000)

    private val payloadSize: Int by option("--payloadSize", help = "The payload size per message.")
        .int().default(150)

    override fun run() {
        repeat(waves) { wave ->
            println("Launching wave ${wave + 1} / ${waves}")

            repeat(jobs) { job ->
                GlobalScope.launch {
                    val client = ClientFactory.createClient(host, port, credentials, ssl)

                    client.connect()

                    repeat(messageCount) {
                        val message = Mqtt3Publish.builder()
                            .topic("internal/kortance/${wave + 1}/${job + 1}")
                            .payload(randomPayload())
                            .build()

                        delay(min = 1, max = 100)

                        client.publish(message)
                    }

                    client.disconnect()
                }
            }

            Thread.sleep(30_000)
        }

        println("All jobs spawned...")
    }

    private suspend fun delay(min: Long, max: Long) {
        delay((min..max).random())
    }

    private fun randomPayload(): ByteArray {
        val payload = ByteArray(payloadSize)
        Random().nextBytes(payload)
        return payload
    }
}
