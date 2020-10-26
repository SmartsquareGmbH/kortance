package de.smartsquare.kortance.scenarios.stress

import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.long
import de.smartsquare.kortance.ClientFactory
import de.smartsquare.kortance.MqttCommand
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StressCommand : MqttCommand("Spawns a wave of clients to test the broker autoscaling.") {

    private val waves: Int by option("-w", "--waves", help = "The number of waves.")
        .int().default(10)

    private val jobs: Int by option("-j", "--jobs", help = "The amount of jobs per wave.")
        .int().default(10)

    private val messageCount: Int by option("-m", "--messages", help = "The amount of messages published per job.")
        .int().default(1000)

    private val waveDelay: Long by option("-d", "--delay", help = "The delay between the waves in milliseconds.")
        .long().default(30_000L)

    override fun run() {
        repeat(waves) { wave ->
            println("Launching wave ${wave + 1} / $waves")

            repeat(jobs) { job ->
                val mqttClient = ClientFactory.createClient(host, port, credentials, ssl)
                val stressClient = StressClient(mqttClient, messageCount, wave + 1, job + 1)

                GlobalScope.launch { stressClient.run() }
            }

            Thread.sleep(waveDelay)
        }

        println("All jobs spawned...")
    }
}
