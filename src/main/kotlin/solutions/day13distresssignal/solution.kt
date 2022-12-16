package solutions.day13distresssignal

import retrieveRowsFromFile
import solutions.day13distresssignal.model.PacketPair
import solutions.day13distresssignal.model.PacketPairContainer

fun sumOfIndicesOfRightOrderedPairs() : Int {
    val rows = retrieveRowsFromFile("inputday13.txt")

    val ppList = rows.windowed(3).map { (a, b, _) -> PacketPair.from(listOf(a, b).joinToString("\n")) }

    val packetPairContainer = PacketPairContainer(ppList)

    return packetPairContainer.selectIndexesRightOrderPairs().sum()
}