package solutions.day13distresssignal.model

class SingleValuePacket(override val internalValue : Int) : IPacket {
    override var elements : List<IPacket> = mutableListOf()

    override fun compare(other: IPacket): CompareResult {
        return when (other) {
            is Packet -> Packet(listOf(this)).compare(other)
            is SingleValuePacket -> internalCompare(other)
        }
    }

    private fun internalCompare(other: SingleValuePacket) : CompareResult {
        return when {
            this.internalValue == other.internalValue -> CompareResult.SAME
            this.internalValue < other.internalValue -> CompareResult.LESS
            else -> CompareResult.MORE
        }
    }

    override fun toString(): String {
        return "[$internalValue]"
    }
}