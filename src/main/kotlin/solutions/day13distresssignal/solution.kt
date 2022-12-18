package solutions.day13distresssignal

import retrieveRowsFromFile
import solutions.day13distresssignal.model.PacketPair
import solutions.day13distresssignal.model.PacketPairContainer

fun sumOfIndicesOfRightOrderedPairs() : Int {
    val rows = retrieveRowsFromFile("inputday13.txt")

    val pairList = rows.windowed(3, step = 3).map { (a, b, _) -> PacketPair.parseFrom(listOf(a, b).joinToString("\n")) }

    val packetPairContainer = PacketPairContainer(pairList)

    return packetPairContainer.selectIndexesRightOrderPairs().sum()

}