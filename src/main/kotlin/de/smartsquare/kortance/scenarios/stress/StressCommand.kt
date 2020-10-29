package de.smartsquare.kortance.scenarios.stress

import de.smartsquare.kortance.CredentialOptions
import de.smartsquare.kortance.WaveOptions
import de.smartsquare.kortance.delay
import de.smartsquare.kortance.mqtt.ClientFactory
import de.smartsquare.kortance.mqtt.SupportedMqttVersion
import de.smartsquare.kortance.randomPayload
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(name = "stress")
class StressCommand : Callable<Int> {

    @CommandLine.Parameters(index = "0", description = ["The hostname of the broker."])
    private lateinit var host: String

    @CommandLine.Parameters(index = "1", description = ["The port of the broker."])
    private var port: Int = 1883

    @CommandLine.Option(names = ["-s", "--ssl"], description = ["If the communication should be encrypted using TLS."])
    private var ssl: Boolean = false

    @CommandLine.ArgGroup(exclusive = false)
    private var credentialOptions: CredentialOptions? = null

    @CommandLine.ArgGroup(exclusive = false)
    private var waveOptions: WaveOptions = WaveOptions()

    @CommandLine.Option(names = ["-j", "--jobs"])
    private var jobs: Int = 10

    @CommandLine.Option(names = ["-m", "--messages"])
    private var messageCount: Int = 1000

    @CommandLine.Option(names = ["-v", "--version"])
    private var mqttVersion: SupportedMqttVersion = SupportedMqttVersion.V3

    override fun call(): Int {
        repeat(waveOptions.waves) { wave ->
            println("Launching wave ${wave + 1} / ${waveOptions.waves}")

            repeat(jobs) { job ->
                val client = ClientFactory.create(host, port, credentialOptions, ssl, mqttVersion)

                GlobalScope.launch {
                    client.connect()

                    repeat(messageCount) {
                        client.publish("internal/kortance/${wave + 1}/${job + 1}", randomPayload(size = 150))

                        delay(min = 1, max = 100)
                    }

                    client.disconnect()
                }
            }

            Thread.sleep(waveOptions.waveDelay)
        }

        println("All jobs spawned...")

        return 0
    }
}
