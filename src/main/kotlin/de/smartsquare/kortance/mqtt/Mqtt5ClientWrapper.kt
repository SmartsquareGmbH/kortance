package de.smartsquare.kortance.mqtt

import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient

class Mqtt5ClientWrapper(private val hivemqClient: Mqtt5BlockingClient) : MqttClient {

    override fun connect() {
        hivemqClient.connect()
    }

    override fun publish(topic: String, payload: ByteArray) {
        hivemqClient.publishWith().topic(topic).payload(payload).send()
    }

    override fun disconnect() {
        hivemqClient.disconnect()
    }
}
