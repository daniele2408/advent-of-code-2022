package solutions.day6tuningtrouble.logic

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SubroutineProtocolKtTest {

    private val expectedResultsStartOfPacket: Map<String, Int> = mapOf(
        "bvwbjplbgvbhsrlpgdmjqwftvncz" to 5,
        "nppdvjthqldpwncqszvftbrmjlhg" to 6,
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 10,
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 11
    )

    private val expectedResultsStartOfMessage: Map<String, Int> = mapOf(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 19,
        "bvwbjplbgvbhsrlpgdmjqwftvncz" to 23,
        "nppdvjthqldpwncqszvftbrmjlhg" to 23,
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 29,
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 26
    )

    @Test
    fun testStartOfPacket() {
        expectedResultsStartOfPacket.forEach {
            assertEquals(it.value, findStartOfPacketMarker(it.key, 4))
        }
    }

    @Test
    fun testStartOfMessage() {
        expectedResultsStartOfMessage.forEach {
            assertEquals(it.value, findStartOfPacketMarker(it.key, 14))
        }
    }

}