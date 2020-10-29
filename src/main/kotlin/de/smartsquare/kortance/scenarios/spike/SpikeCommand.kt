package de.smartsquare.kortance.scenarios.spike

import de.smartsquare.kortance.CredentialOptions
import de.smartsquare.kortance.mqtt.ClientFactory
import de.smartsquare.kortance.mqtt.SupportedMqttVersion
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

    @CommandLine.Option(names = ["-v", "--version"])
    private var mqttVersion: SupportedMqttVersion = SupportedMqttVersion.V3

    override fun call(): Int {
        with(ClientFactory.create(host, port, credentialOptions, ssl, mqttVersion)) {
            print("Publishing $messageCount messages... ")

            connect()

            repeat(messageCount) {
                publish("internal/kortance", randomPayload(size = 150))
            }

            disconnect()
        }

        println("done.")

        return 0
    }
}
