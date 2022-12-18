package solutions.day13distresssignal.model

import solutions.day13distresssignal.logic.Parser

class PacketPair(private val packets : Pair<IPacket, IPacket>) {

    fun arePacketsInRightOrder() : Boolean {
        return packets.first.compare(packets.second) == CompareResult.LESS
    }

    companion object {

        fun parseFrom(s : String) : PacketPair {
            val (rowA, rowB) = s.split('\n')

            return PacketPair(Parser.parse(rowA) to Parser.parse(rowB))
        }
    }

}