package de.beckmann.phoenix

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random

class Scalability : CliktCommand("Spawns ten waves of ten clients where each client publishes 1000 messages with a random delay.") {

    val host: String by argument(name = "host", help = "The broker host (test.mosquitto.org)")

    val port: Int by argument(name = "port", help = "The broker port (typically 1883 or 8883)").int()

    val username: String? by option(help = "The username or empty if anonymous is allowed")

    val password: String? by option(help = "The password or empty if anonymous is allowed")

    val ssl: Boolean by option(help = "If the test clients should establish a secure connection").flag()

    override fun run() {
        repeat(10) { wave ->
            println("Launching wave ${wave + 1}")

            repeat(10) { job ->
                GlobalScope.launch {
                    val client = ClientFactory.createClient(host, port, username, password, ssl)

                    client.connect()

                    repeat(1000) {
                        val message = Mqtt3Publish.builder()
                            .topic("internal/kortance/${wave + 1}/${job + 1}")
                            .payload(randomPayload())
                            .build()

                        delay((0..100).random().toLong())

                        client.publish(message)
                    }

                    client.disconnect()
                }
            }

            Thread.sleep(30_000)
        }

        println("All jobs spawned...")
    }

    private fun randomPayload(): ByteArray {
        val payload = ByteArray(150)
        Random().nextBytes(payload)
        return payload
    }
}

fun main(args: Array<String>) = Scalability().main(args)
