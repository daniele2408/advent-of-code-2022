package solutions.day13distresssignal.model

import org.junit.jupiter.api.Assertions.*
import retrieveRowsFromFile
import solutions.day13distresssignal.logic.Parser
import kotlin.test.Test
import kotlin.test.fail

class ParserTest {

    @Test
    fun testParse() {

        val inputOutput : Map<String, String> = mapOf(
            "[1,[2,[3,[4,[5,6,7]]]],8,9]" to "[5,6,7]",
            "[[4,4],4,4]" to "[4,4]",
            "[]" to "[]",
            "[7,7,7,7]" to "[7,7,7,7]",
            "[[1],[2,3,4]]" to "[2,3,4]"
        )

        inputOutput.entries.forEach { (k,v) ->
            assertEquals(v,  Parser.parseLastInternalBrackets(k))
        }

    }

    @Test
    fun testExtract() {
        val res = Packet.createPacket("[[4,4],4,4]")

        assertTrue(res.getElement(0) is Packet)
        assertTrue(res.getElement(0).getElement(0) is SingleValuePacket)
        assertEquals(4, (res.getElement(0).getElement(0) as SingleValuePacket).internalValue)
        assertTrue(res.getElement(0).getElement(0) is SingleValuePacket)
        assertTrue(res.getElement(1) is SingleValuePacket)
        assertTrue(res.getElement(2) is SingleValuePacket)

    }

    @Test
    fun testExtract2() {
        val res = Packet.createPacket("[7,7,7,7]")

        res.asSeq().all { it is SingleValuePacket && it.internalValue == 7 }

    }

    @Test
    fun testExtract3() {
        val res = Packet.createPacket("[1,[2,[3,[4,[5,6,7]]]],8,9]")

        assertEquals(4, res.asSeq().count())
        assertTrue(res.getElement(0) is SingleValuePacket)
        assertTrue(res.getElement(1) is Packet)
        assertTrue(res.getElement(2) is SingleValuePacket)
        assertTrue(res.getElement(3) is SingleValuePacket)
        assertEquals(2, res.getElement(1).asSeq().count())
        assertEquals(2, res.getElement(1).getElement(1).asSeq().count())
        assertEquals(2, res.getElement(1).getElement(1).getElement(1).asSeq().count())
        assertEquals(3, res.getElement(1).getElement(1).getElement(1).getElement(1).asSeq().count())

    }

    @Test
    fun testExtract4() {
        val res = Packet.createPacket("[[1],[2,3,4]]")

        assertEquals(2, res.asSeq().count())
        assertTrue(res.getElement(0) is Packet)
        assertTrue(res.getElement(1) is Packet)
        assertEquals(3, res.getElement(1).asSeq().count())


    }


    @Test
    fun testExtractEmpty() {
        val packet = Packet.createPacket("[[[]]]")

        assertEquals(0, packet.size())
        assertTrue(packet is EmptyPacket)
    }

    @Test
    fun testCompare() {

        val packetA = Packet.createPacket("[1,1,3,1,1]")
        val packetB = Packet.createPacket("[1,1,5,1,1]")

        assertTrue(packetA.isRightOrder(packetB))

        val packetC = Packet.createPacket("[1,[2,[3,[4,[5,6,7]]]],8,9]")
        val packetD = Packet.createPacket("[1,[2,[3,[4,[5,6,0]]]],8,9]")

        assertFalse(packetC.isRightOrder(packetD))

        val packetE = Packet.createPacket("[7,7,7,7]")
        val packetF = Packet.createPacket("[7,7,7]")

        assertFalse(packetE.isRightOrder(packetF))

        val packetG = Packet.createPacket("[[[]]]")
        val packetH = Packet.createPacket("[[]]")

        assertFalse(packetG.isRightOrder(packetH))
        assertTrue(packetH.isRightOrder(packetG))

    }

    @Test
    fun testSamplePart1() {
        val rows = retrieveRowsFromFile("inputday13sample.txt")

        val ppList = rows.windowed(3, step = 3).map { (a, b, _) -> PacketPair.from(listOf(a, b).joinToString("\n")) }

        val packetPairContainer = PacketPairContainer(ppList)

        val res = packetPairContainer.selectIndexesRightOrderPairs().sum()

        assertEquals(13, res)
    }
}