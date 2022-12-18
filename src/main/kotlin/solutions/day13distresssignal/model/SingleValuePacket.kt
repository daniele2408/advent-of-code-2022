package solutions.day13distresssignal.model

class SingleValuePacket(override val internalValue : Int) : IPacket {
    override var elements : List<IPacket> = mutableListOf()



    override fun compare(other: IPacket): Boolean {
        return when {
            other is Packet -> Packet(listOf(this)).compare(other)
            else -> internalValue < (other as SingleValuePacket).internalValue
        }
    }

    override fun toString(): String {
        return "[$internalValue]"
    }
}