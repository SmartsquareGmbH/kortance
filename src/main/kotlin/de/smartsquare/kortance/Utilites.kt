package de.smartsquare.kortance

import java.util.Random
import kotlinx.coroutines.delay as delayCoroutine

suspend fun delay(min: Long, max: Long) {
    delayCoroutine((min..max).random())
}

fun randomPayload(size: Int): ByteArray {
    val payload = ByteArray(size)
    Random().nextBytes(payload)
    return payload
}
