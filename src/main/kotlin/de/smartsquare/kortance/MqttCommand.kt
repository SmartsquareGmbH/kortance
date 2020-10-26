package de.smartsquare.kortance

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

abstract class MqttCommand(help: String) : CliktCommand(help) {

    protected val host: String by argument(name = "host", help = "The broker host (test.mosquitto.org)")

    protected val port: Int by argument(name = "port", help = "The broker port (typically 1883 or 8883)")
        .int()

    protected val ssl: Boolean by option("-s", "--secure", help = "If the connection should be established with tls.")
        .flag()

    private val username by option(
        "-u",
        "--username",
        envvar = "KORTANCE_MQTT_USERNAME",
        help = "The username or blank if anonymous is allowed"
    )

    private val password by option(
        "-p",
        "--password",
        envvar = "KORTANCE_MQTT_PASSWORD",
        help = "The password or blank if anonymous is allowed"
    )

    protected val credentials = if (password != null && username != null) {
        Credentials(username!!, password!!)
    } else {
        null
    }
}
