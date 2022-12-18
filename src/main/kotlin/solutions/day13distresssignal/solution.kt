package solutions.day13distresssignal

import retrieveRowsFromFile
import solutions.day13distresssignal.logic.Parser
import solutions.day13distresssignal.model.Packet
import solutions.day13distresssignal.model.PacketPair
import solutions.day13distresssignal.model.PacketPairContainer
import solutions.day13distresssignal.model.SingleValuePacket

fun sumOfIndicesOfRightOrderedPairs() : Int {
    val rows = retrieveRowsFromFile("inputday13.txt")

    val pairList = rows.windowed(3, step = 3).map { (a, b, _) -> PacketPair.parseFrom(listOf(a, b).joinToString("\n")) }

    val packetPairContainer = PacketPairContainer(pairList)

    return packetPairContainer.selectIndexesRightOrderPairs().sum()

}

fun getDecoderKey() : Int {

    val rows = retrieveRowsFromFile("inputday13.txt")

    val packetPairsList = rows.filter { it.isNotBlank() }.map { Parser.parse(it) }

    val firstDividerPacket = Packet(SingleValuePacket(2))
    val secondDividerPacket = Packet(SingleValuePacket(6))

    val sortedPackets = (listOf(firstDividerPacket, secondDividerPacket) + packetPairsList).sortedWith { o1, o2 -> o1.compare(o2).value }

    val indexOfFirstDivider = sortedPackets.indexOf(firstDividerPacket)+1
    val indexOfSecondDivider = sortedPackets.indexOf(secondDividerPacket)+1

    return indexOfFirstDivider * indexOfSecondDivider
}