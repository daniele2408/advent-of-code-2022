package solutions.day13distresssignal.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import retrieveRowsFromFile
import solutions.day13distresssignal.logic.Parser
import kotlin.test.Test

class ParserTest {

    @Test
    fun testExtract() {
        val message = "[[4,4],4,4]"
        val (res, _) = Parser.parseString(EmptyPacket(), message)

        assertTrue(res.getElement(0) is Packet)
        assertTrue(res.getElement(0).getElement(0) is SingleValuePacket)
        assertEquals(4, (res.getElement(0).getElement(0) as SingleValuePacket).internalValue)
        assertTrue(res.getElement(0).getElement(0) is SingleValuePacket)
        assertTrue(res.getElement(1) is SingleValuePacket)
        assertTrue(res.getElement(2) is SingleValuePacket)

    }

    @Test
    fun testExtract2() {
        val message = "[7,7,7,7]"
        val (res, _) = Parser.parseString(EmptyPacket(), message)

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
    fun testExtract3Bis() {
        val (res, _) = Parser.parseString(EmptyPacket(), "[1,[2,[3,[4,[5,6,7]]]],8,9]")

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
    fun testExtract4Bis() {
        val message = "[[1],[2,3,4]]"
        val (res, _) = Parser.parseString(EmptyPacket(), message)

        assertEquals(2, res.asSeq().count())
        assertTrue(res.getElement(0) is Packet)
        assertTrue(res.getElement(1) is Packet)
        assertEquals(3, res.getElement(1).asSeq().count())

    }

    @Test
    fun testExtractEmpty() {
        val message = "[[[]]]"
        val packet = Packet.createPacket(message)

        assertEquals(3, packet.size())
    }

    @Test
    fun testExtractEmpty2() {
        val message = "[[[]]]"
        val packet = Parser.parse(message)

        assertEquals(1, packet.size())
    }

    @Test
    fun testCompare() {

        val packetA = Packet.createPacket("[1,1,3,1,1]")
        val packetB = Packet.createPacket("[1,1,5,1,1]")

        assertEquals(CompareResult.LESS, packetA.compare(packetB))

        val packetC = Packet.createPacket("[1,[2,[3,[4,[5,6,7]]]],8,9]")
        val packetD = Packet.createPacket("[1,[2,[3,[4,[5,6,0]]]],8,9]")

        assertEquals(CompareResult.MORE, packetC.compare(packetD))

        val packetE = Packet.createPacket("[7,7,7,7]")
        val packetF = Packet.createPacket("[7,7,7]")

        assertEquals(CompareResult.MORE, packetE.compare(packetF))

        val packetG = Packet.createPacket("[[[]]]")
        val packetH = Packet.createPacket("[[]]")

        assertEquals(CompareResult.MORE, packetG.compare(packetH))
        assertEquals(CompareResult.LESS, packetH.compare(packetG))

    }

    @Test
    fun testCompareParser() {

        val packetA = Parser.parse("[1,1,3,1,1]")
        val packetB = Parser.parse("[1,1,5,1,1]")

        assertEquals(CompareResult.LESS, packetA.compare(packetB))

        val packetE = Parser.parse("[7,7,7,7]")
        val packetF = Parser.parse("[7,7,7]")

        assertEquals(CompareResult.MORE, packetE.compare(packetF))

        val packetG = Parser.parse("[[[]]]")
        val packetH = Parser.parse("[[]]")

        assertEquals(CompareResult.MORE, packetG.compare(packetH))
        assertEquals(CompareResult.LESS, packetH.compare(packetG))
    }


    @Test
    fun testCompareParser2() {

        mapOf(
            ("[1,1,3,1,1]" to "[1,1,5,1,1]") to CompareResult.LESS,
            ("[7,7,7,7]" to "[7,7,7]") to CompareResult.MORE,
            ("[[1],[2,3,4]]" to "[[1],4]") to CompareResult.LESS,
            ("[1,[2,[3,[4,[5,6,7]]]],8,9]" to "[1,[2,[3,[4,[5,6,0]]]],8,9]") to CompareResult.MORE,
            ("[[[]]]" to "[[]]") to CompareResult.MORE
        ).forEach { (t, u) ->
            assertEquals(u, Parser.parse(t.first).compare(Parser.parse(t.second)), "Wrong result for pair $t")
        }

    }

    @Test
    fun testCompareParser3() {

        val packetC = Parser.parse("[9]")
        val packetD = Parser.parse("[[8,7,6]]")

        assertEquals(CompareResult.MORE, packetC.compare(packetD))

    }

    @Test
    fun testCompareParser4() {

        val packetC = Parser.parse("[[1],[2,3,4]]")
        val packetD = Parser.parse("[[1],4]")

        assertEquals(CompareResult.LESS, packetC.compare(packetD))

    }

    @Test
    fun testCompareParser5() {

        val packetC = Parser.parse("[[4,4],4,4]")
        val packetD = Parser.parse("[[4,4],4,4,4]")

        assertEquals(CompareResult.LESS, packetC.compare(packetD))

    }

    @Test
    fun testSamplePart1() {
        val rows = retrieveRowsFromFile("inputday13sample.txt")

        val packetPairsList = rows.windowed(3, step = 3).map { (a, b, _) -> PacketPair.parseFrom(listOf(a, b).joinToString("\n")) }

        val packetPairContainer = PacketPairContainer(packetPairsList)

        val res = packetPairContainer.selectIndexesRightOrderPairs().sum()

        assertEquals(13, res)
    }

    @Test
    fun testSamplePart2() {
        val rows = retrieveRowsFromFile("inputday13sample.txt")

        val packetPairsList = rows.filter { it.isNotBlank() }.map { Parser.parse(it) }

        val firstDividerPacket = Packet(SingleValuePacket(2))
        val secondDividerPacket = Packet(SingleValuePacket(6))

        val sortedPackets = (listOf(firstDividerPacket, secondDividerPacket) + packetPairsList).sortedWith { o1, o2 -> o1.compare(o2).value }

        val indexOfFirstDivider = sortedPackets.indexOf(firstDividerPacket)+1
        val indexOfSecondDivider = sortedPackets.indexOf(secondDividerPacket)+1

        assertEquals(140, indexOfFirstDivider * indexOfSecondDivider)
    }

}