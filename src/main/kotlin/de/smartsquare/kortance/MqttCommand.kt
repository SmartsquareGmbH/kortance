package de.smartsquare.kortance

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

abstract class MqttCommand(help: String) : CliktCommand(help) {

    protected val host: String by argument(name = "host", help = "The broker host (test.mosquitto.org)")

    protected val port: Int by argument(name = "port", help = "The broker port (typically 1883 or 8883)").int()

    private val username: String? by option("-u", "--username", help = "The username or empty if anonymous is allowed")

    private val password: String? by option("-p", "--password", help = "The password or empty if anonymous is allowed")

    protected val ssl: Boolean by option("-s", "--secure", help = "SSL Connection").flag()

    protected val credentials = if (username != null && password != null) {
        Credentials(username!!, password!!)
    } else {
        null
    }
}
