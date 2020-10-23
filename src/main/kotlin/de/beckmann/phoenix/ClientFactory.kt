package de.beckmann.phoenix

import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client

object ClientFactory {

    fun createClient(host: String, port: Int, username: String?, password: String?, ssl: Boolean): Mqtt3BlockingClient {
        val baseClient = Mqtt3Client.builder()
            .serverHost(host)
            .serverPort(port)

        val authClient = if (username != null && password != null) {
            baseClient
                .simpleAuth()
                .username(username)
                .password(password.toByteArray())
                .applySimpleAuth()
        } else {
            baseClient
        }

        return if (ssl) {
            authClient.sslWithDefaultConfig().build().toBlocking()
        } else {
            authClient.build().toBlocking()
        }
    }
}
