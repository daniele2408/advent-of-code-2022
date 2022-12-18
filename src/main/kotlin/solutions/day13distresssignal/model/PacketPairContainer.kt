package solutions.day13distresssignal.model

class PacketPairContainer(private val packetPairs: List<PacketPair>) {

    fun selectIndexesRightOrderPairs() : List<Int> {
        return packetPairs
            .mapIndexed { index, packetPair -> index to packetPair.arePacketsInRightOrder() }
            .filter { it.second }
            .map { it.first + 1 }
    }

}