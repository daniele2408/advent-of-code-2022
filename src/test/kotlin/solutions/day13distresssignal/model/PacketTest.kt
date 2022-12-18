package solutions.day13distresssignal.model

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class PacketTest {

    @Test
    fun testPlus() {

        val packetA = Packet(
            listOf(
                SingleValuePacket(1),
                SingleValuePacket(2),
                SingleValuePacket(3)
            )
        )

        val packetB = Packet(
            listOf(
                SingleValuePacket(4),
                SingleValuePacket(5)
            )
        )

        val packetC = packetA + packetB

        assertEquals(5, packetC.size())

        val packetD = packetA + SingleValuePacket(10)

        assertEquals(4, packetD.size())
        assertTrue(packetD.getElement(3) is SingleValuePacket)
        assertEquals(10, packetD.getElement(3).internalValue)

        val packetE = SingleValuePacket(11) + SingleValuePacket(12)
        assertEquals(2, packetE.size())

        assertTrue(packetE.asSeq().all { it is SingleValuePacket })
        assertEquals(11, packetE.getElement(0).internalValue)
        assertEquals(12, packetE.getElement(1).internalValue)
    }

}