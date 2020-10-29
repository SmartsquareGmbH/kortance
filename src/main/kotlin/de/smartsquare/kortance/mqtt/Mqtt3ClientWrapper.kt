package de.smartsquare.kortance.mqtt

import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient

class Mqtt3ClientWrapper(private val hivemqClient: Mqtt3BlockingClient) : MqttClient {

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
