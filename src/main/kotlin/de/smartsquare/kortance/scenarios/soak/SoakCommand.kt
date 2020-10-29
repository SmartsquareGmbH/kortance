package de.smartsquare.kortance.scenarios.soak

import de.smartsquare.kortance.ClientFactory
import de.smartsquare.kortance.CredentialOptions
import de.smartsquare.kortance.WaveOptions
import de.smartsquare.kortance.delay
import de.smartsquare.kortance.randomPayload
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(name = "soak")
class SoakCommand : Callable<Int> {

    @CommandLine.Parameters(index = "0", description = ["The hostname of the broker."])
    private lateinit var host: String

    @CommandLine.Parameters(index = "1", description = ["The port of the broker."])
    private var port: Int = 1883

    @CommandLine.Option(names = ["-s", "--ssl"], description = ["If the communication should be encrypted using TLS."])
    private var ssl: Boolean = false

    @CommandLine.ArgGroup(exclusive = false)
    private var credentialOptions: CredentialOptions? = null

    @CommandLine.ArgGroup(exclusive = false)
    private lateinit var waveOptions: WaveOptions

    @CommandLine.Option(names = ["-j", "--jobs"])
    private var jobs: Int = 10

    override fun call(): Int {
        repeat(waveOptions.waves) { wave ->
            println("Launching wave ${wave + 1} / ${waveOptions.waves}")

            repeat(jobs) { job ->
                val client = ClientFactory.createClient(host, port, credentialOptions, ssl)

                GlobalScope.launch {
                    client.connect()

                    try {
                        while (true) {
                            client.publishWith()
                                .topic("internal/kortance/${wave + 1}/${job + 1}")
                                .payload(randomPayload(size = 150))
                                .send()

                            delay(min = 1, max = 100)
                        }
                    } finally {
                        client.disconnect()
                    }
                }
            }

            Thread.sleep(waveOptions.waveDelay)
        }

        println("All jobs spawned. Waiting for manual termination...")

        return 0
    }
}
