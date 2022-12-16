package solutions.day13distresssignal.model

class SingleValuePacket(override val internalValue : Int) : IPacket {
    override val elements : MutableList<IPacket> = mutableListOf()

    override fun isRightOrder(other: IPacket): Boolean {
        return when {
            other is Packet -> Packet(listOf(this)).isRightOrder(other)
            else -> internalValue <= (other as SingleValuePacket).internalValue
        }
    }
}