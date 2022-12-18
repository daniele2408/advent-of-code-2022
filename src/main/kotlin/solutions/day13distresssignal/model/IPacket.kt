package solutions.day13distresssignal.model

interface IPacket {
    var elements : List<IPacket>
    val internalValue : Int

    fun size() : Int {
        return elements.size
    }

    fun asSeq() : Sequence<IPacket> {
        return elements.asSequence()
    }

    fun flatten() : List<IPacket> {
        return elements.map { it.elements }.flatten()
    }

    fun addElement(packet: IPacket) {
        elements += packet
    }

    fun getElement(idx : Int) : IPacket {
        return elements[idx]
    }

    fun getElementOpt(idx : Int) : IPacket? {
        return if (idx > elements.size-1) null
        else elements[idx]
    }

    fun compare(other: IPacket) : Boolean

    operator fun plus(other: IPacket): IPacket {
        return when {
            this is Packet && other is Packet -> Packet(this.elements + other.elements)
            this is Packet && other is SingleValuePacket -> Packet(this.elements + listOf(other))
            this is SingleValuePacket && other is Packet -> Packet(this.elements + listOf(other))
            this is SingleValuePacket && other is SingleValuePacket -> Packet(listOf(this, other))
            else -> throw RuntimeException("We're concatenating an illegal combination of IPackets concrete classes")
        }
    }

}