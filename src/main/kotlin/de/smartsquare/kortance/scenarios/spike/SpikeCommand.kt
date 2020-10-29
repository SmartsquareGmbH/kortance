package de.smartsquare.kortance.scenarios.spike

import de.smartsquare.kortance.ClientFactory
import de.smartsquare.kortance.CredentialOptions
import de.smartsquare.kortance.randomPayload
import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(name = "spike")
class SpikeCommand : Callable<Int> {

    @CommandLine.Parameters(index = "0", description = ["The hostname of the broker."])
    private lateinit var host: String

    @CommandLine.Parameters(index = "1", description = ["The port of the broker."])
    private var port: Int = 1883

    @CommandLine.Option(names = ["-s", "--ssl"], description = ["If the communication should be encrypted using TLS."])
    private var ssl: Boolean = false

    @CommandLine.ArgGroup(exclusive = false)
    private var credentialOptions: CredentialOptions? = null

    @CommandLine.Option(names = ["-m", "--messages"])
    private var messageCount: Int = 1000

    override fun call(): Int {
        with(ClientFactory.createClient(host, port, credentialOptions, ssl)) {
            print("Publishing $messageCount messages... ")

            connect()

            repeat(messageCount) {
                publishWith().topic("internal/kortance").payload(randomPayload(size = 150)).send()
            }

            disconnect()
        }

        println("done.")

        return 0
    }
}
