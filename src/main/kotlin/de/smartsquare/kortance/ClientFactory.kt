package de.smartsquare.kortance

import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client

object ClientFactory {

    fun createClient(host: String, port: Int, credentials: Credentials?, ssl: Boolean): Mqtt3BlockingClient {
        val baseClient = Mqtt3Client.builder()
            .serverHost(host)
            .serverPort(port)

        val authClient = if (credentials != null) {
            baseClient
                .simpleAuth()
                .username(credentials.username)
                .password(credentials.password.toByteArray())
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
