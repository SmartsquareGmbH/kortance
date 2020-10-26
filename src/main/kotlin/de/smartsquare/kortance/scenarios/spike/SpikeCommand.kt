package de.smartsquare.kortance.scenarios.spike

import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import de.smartsquare.kortance.ClientFactory
import de.smartsquare.kortance.MqttCommand
import de.smartsquare.kortance.randomPayload

class SpikeCommand : MqttCommand("Publishes a defined number of messages in the shortes period of time.") {

    private val messageCount: Int by option("-m", "--messages", help = "The amount of messages published per job.")
        .int().default(1000)

    override fun run() {
        with(ClientFactory.createClient(host, port, credentials, ssl)) {
            print("Publishing $messageCount messages... ")

            connect()

            repeat(messageCount) {
                publishWith().topic("internal/kortance").payload(randomPayload(size = 150)).send()
            }

            disconnect()
        }

        println("done.")
    }
}
