package solutions.day13distresssignal.model

interface IPacket {
    val elements : List<IPacket>
    val internalValue : Int

    fun asSeq() : Sequence<IPacket> {
        return elements.asSequence()
    }

    fun getElement(idx : Int) : IPacket {
        return elements[idx]
    }

    fun isRightOrder(other: IPacket) : Boolean

}