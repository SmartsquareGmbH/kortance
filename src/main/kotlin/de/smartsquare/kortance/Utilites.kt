package de.smartsquare.kortance

import java.util.Random
import kotlinx.coroutines.delay as delayCoroutine

/**
 * This function delays the current coroutine with a random delay between [min] and [max].
 */
suspend fun delay(min: Long, max: Long) {
    delayCoroutine((min..max).random())
}

/**
 * This method generates a random ByteArray with the given [size].
 */
fun randomPayload(size: Int): ByteArray {
    val payload = ByteArray(size)
    Random().nextBytes(payload)
    return payload
}
