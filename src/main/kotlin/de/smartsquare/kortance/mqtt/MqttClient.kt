package de.smartsquare.kortance.mqtt

interface MqttClient {

    fun publish(topic: String, payload: ByteArray)

    fun connect()

    fun disconnect()
}
