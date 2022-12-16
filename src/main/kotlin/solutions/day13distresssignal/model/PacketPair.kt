package solutions.day13distresssignal.model

class PacketPair(private val packets : Pair<IPacket, IPacket>) {

    fun arePacketsInRightOrder() : Boolean {
        return packets.first.isRightOrder(packets.second)
    }

    companion object {

        fun from(s : String) : PacketPair {
            val (rowA, rowB) = s.split('\n')

            return PacketPair(Packet.createPacket(rowA) to Packet.createPacket(rowB))
        }
    }

}