package de.smartsquare.kortance.mqtt

import com.hivemq.client.mqtt.mqtt3.Mqtt3Client
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import de.smartsquare.kortance.CredentialOptions

object ClientFactory {

    fun create(host: String, port: Int, cred: CredentialOptions?, ssl: Boolean, ver: SupportedMqttVersion): MqttClient {
        return if (ver == SupportedMqttVersion.V5) {
            buildFive(host, port, cred, ssl)
        } else {
            buildThree(host, port, cred, ssl)
        }
    }

    private fun buildThree(host: String, port: Int, credentials: CredentialOptions?, ssl: Boolean): Mqtt3ClientWrapper {
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
            Mqtt3ClientWrapper(authClient.sslWithDefaultConfig().build().toBlocking())
        } else {
            Mqtt3ClientWrapper(authClient.build().toBlocking())
        }
    }

    private fun buildFive(host: String, port: Int, credentials: CredentialOptions?, ssl: Boolean): Mqtt5ClientWrapper {
        val baseClient = Mqtt5Client.builder()
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
            Mqtt5ClientWrapper(authClient.sslWithDefaultConfig().build().toBlocking())
        } else {
            Mqtt5ClientWrapper(authClient.build().toBlocking())
        }
    }
}
