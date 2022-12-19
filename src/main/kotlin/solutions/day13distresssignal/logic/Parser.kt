package solutions.day13distresssignal.logic

import solutions.day13distresssignal.model.EmptyPacket
import solutions.day13distresssignal.model.IPacket
import solutions.day13distresssignal.model.Packet

class Parser {

    companion object {

        fun parse(message: String): IPacket {
            val (iPacket, _) = parseString(EmptyPacket(), message)
            return iPacket
        }

        fun parseString(acc: IPacket, message: String) : Pair<IPacket, String> {

            if (message.isEmpty()) {
                return acc.getElement(0) to message
            }
            if (message.first() == ']') return acc to message.substring(1)

            else {
                val head = message.first()
                val rest = message.substring(1)

                if (head != '[') {
                    //aggiungi

                    val chunk = head + (rest).takeWhile { it != '[' && it != ']' }
                    val newRest = message.substring(chunk.length)
                    return parseString(acc + Packet.createPacket(chunk), newRest)

                }
                // scendi
                val (packet, msg) = parseString(EmptyPacket(), rest)
                acc.addElement(packet)
                return parseString(acc, msg)

            }
        }

    }

}