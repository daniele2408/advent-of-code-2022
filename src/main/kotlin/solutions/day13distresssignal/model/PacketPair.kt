package solutions.day13distresssignal.model

import solutions.day13distresssignal.logic.Parser

class PacketPair(private val packets : Pair<IPacket, IPacket>) {

    fun arePacketsInRightOrder() : Boolean {
        val rightOrder = packets.first.compare(packets.second)
        println("${packets.first} vs ${packets.second} ${if (rightOrder) "are" else "are NOT"} in right order.")
        return rightOrder
    }

    companion object {

        fun from(s : String) : PacketPair {
            val (rowA, rowB) = s.split('\n')

            return PacketPair(Packet.createPacket(rowA) to Packet.createPacket(rowB))
        }

        fun parseFrom(s : String) : PacketPair {
            val (rowA, rowB) = s.split('\n')

            return PacketPair(Parser.parse(rowA) to Parser.parse(rowB))
        }
    }

}