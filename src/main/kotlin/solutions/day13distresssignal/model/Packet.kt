package solutions.day13distresssignal.model

import javax.swing.plaf.metal.OceanTheme

open class Packet(override val elements: List<IPacket>) : IPacket {

    override val internalValue : Int = -1

    fun size() : Int {
        return elements.size
    }

    override fun isRightOrder(other: IPacket): Boolean {
        return when (other) {
            is SingleValuePacket -> isRightOrder(Packet(listOf(other)))
            else -> isRightOrder(other as Packet)
        }
    }

    private fun isRightOrder(other: Packet) : Boolean {
        val zippedSeq: Sequence<Pair<IPacket, IPacket>> = this.asSeq() zip other.asSeq()
        val allRightOrders = zippedSeq.all { (a, b) -> a.isRightOrder(b) }
        return if (!allRightOrders) false
        else {
            return if (this.size() == other.size()) true
            else this.size() < other.size()
        }
    }

    companion object {
        private infix fun Int.startOpenEndClose(other: Int): IntRange = IntRange(this+1, other)

        fun createPacket(s : String) : Packet {

            if (!s.contains(',')) return Packet((0 until s.count { it == '[' }).map { _ -> EmptyPacket() }.toList())

            val trimmedS = s.removePrefix("[").removeSuffix("]")

            val posFirstOpenBracket = trimmedS.indexOfFirst { it == '[' }
            val posLastClosedBracket = trimmedS.indexOfLast { it == ']' }

            if (posFirstOpenBracket == -1) return Packet(trimmedS.split(',').map {  SingleValuePacket(it.toInt()) })

            val valuesBeforeBracket : List<Int> = trimmedS
                .substring(0, posFirstOpenBracket).split(',')
                .filter { it != "" }
                .map { it.toInt() }

            val chunkBracketed : String = trimmedS
                .substring(posFirstOpenBracket .. posLastClosedBracket)

            val valuesAfterBracket : List<Int> = trimmedS
                .substring(posLastClosedBracket startOpenEndClose trimmedS.lastIndex).split(',')
                .filter { it != "" }
                .map { it.toInt() }

            if (chunkBracketed.isEmpty()) return Packet(
                (valuesBeforeBracket + valuesAfterBracket).map { SingleValuePacket(it) }
            )

            return from(valuesBeforeBracket, chunkBracketed, valuesAfterBracket)

        }

        fun from(valuesBefore : List<Int>, middleBracket : String, valuesAfter : List<Int> ) : Packet {

            val listBefore = valuesBefore.map { SingleValuePacket(it) }
            val middlePacket = Packet.createPacket(middleBracket)
            val listAfter = valuesAfter.map { SingleValuePacket(it) }

            return Packet(listBefore + middlePacket + listAfter)

        }

    }

}