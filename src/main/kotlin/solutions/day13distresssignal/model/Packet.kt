package solutions.day13distresssignal.model

open class Packet(override var elements: List<IPacket>) : IPacket {

    override val internalValue : Int = -1



    override fun compare(other: IPacket): Boolean {
        return when (other) {
            is SingleValuePacket -> isRightOrder(Packet(listOf(other)))
            else -> isRightOrder(other as Packet)
        }
    }

    private fun isRightOrder(other: Packet) : Boolean {
//        println("Comparing $this vs $other")

        val maxLen = maxOf(this.size(), other.size())

        for (i in (0 until maxLen)) {
            val thisEl = this.getElementOpt(i)
            val otherEl = other.getElementOpt(i)

            if (thisEl == null) return true
            if (otherEl == null) return false

            if (this.getElement(i).compare(other.getElement(i))) return true
        }

        return false
    }

    private fun isRightOrderOld(other: Packet) : Boolean {
//        println("Comparing $this vs $other")
        val zippedSeq: Sequence<Pair<IPacket, IPacket>> = this.asSeq() zip other.asSeq()
        val seqFilterSameValues = zippedSeq.filter { (a, b) -> a.internalValue != b.internalValue }.toList()
        if (seqFilterSameValues.isEmpty()) return true
        val firstPairNonEqual = seqFilterSameValues.first()
        val allRightOrders = firstPairNonEqual.first.internalValue < firstPairNonEqual.second.internalValue
        val res = if (!allRightOrders) false
        else {
            if (this.size() == other.size()) true
            else this.size() < other.size()
        }
//        println("Comparing $this vs $other ${if (res) "are" else "are not"} in the right order")
        return res
    }

    override fun toString(): String {
        return "Packet(${elements.map { it }.joinToString(", ")})"
    }

    companion object {
        private infix fun Int.startOpenEndClose(other: Int): IntRange = IntRange(this+1, other)

        fun createPacket(s : String) : Packet {

            if (!s.contains(',')) {
                if (s.all { it.isDigit() }) {
                    return Packet(listOf(SingleValuePacket(s.toInt())))
                }
                return Packet((0 until s.count { it == '[' }).map { _ -> EmptyPacket() }.toList())
            }

            val trimmedS = s.removePrefix("[").removeSuffix("]")

            val posFirstOpenBracket = trimmedS.indexOfFirst { it == '[' }
            val posLastClosedBracket = trimmedS.indexOfLast { it == ']' }



            if (posFirstOpenBracket == -1) return Packet(trimmedS.split(',')
                .filter { it.isNotBlank() }
                .map {  SingleValuePacket(it.toInt()) })

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