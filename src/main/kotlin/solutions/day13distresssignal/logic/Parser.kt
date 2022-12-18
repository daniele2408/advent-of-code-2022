package solutions.day13distresssignal.logic

import solutions.day13distresssignal.model.EmptyPacket
import solutions.day13distresssignal.model.IPacket
import solutions.day13distresssignal.model.Packet

class Parser {

    companion object {

        fun extractInternalBracketsContent(s : String) : String {
            if (!s.contains('[')) return ""

            val posLastOpenBracket = s.indexOfLast { it == '[' }
            val posFirstClosedBracket = s.indexOfFirst { it == ']' }

            s.split(',')

            return s.substring(posLastOpenBracket+1, posFirstClosedBracket)
        }

        fun parseLastInternalBrackets(s : String) : String {
            return Regex("\\[(([0-9]+,)+(''|[0-9]+))\\]").find(s)?.value ?: "[]"
        }

        fun parse(message: String): IPacket {
            val (iPacket, _) = parseString(EmptyPacket(), message)
            return iPacket
        }

        fun compareStrings(stringA: String, stringB: String) : Boolean {

            if (containsDigit(stringA) && containsDigit(stringB)) {
                val listA = extractInts(stringA)
                val listB = extractInts(stringB)

                val res = (listA zip listB).takeWhile { (a, b) -> a <= b }

                if (res.isEmpty()) return false
                if (res[0].first < res[0].second) return true

                return listA.size < listB.size
            }

            val listA = if (containsDigit(stringA)) extractInts(stringA) else (0 until stringA.count { it == '[' }).map { _ -> -1 }.toList()
            val listB = if (containsDigit(stringB)) extractInts(stringB) else (0 until stringB.count { it == '[' }).map { _ -> -1 }.toList()

            return listA.size < listB.size

        }

        private fun containsDigit(s: String): Boolean {
            return s.any{ it.isDigit() }
        }

        private fun extractInts(stringA: String) =
            stringA.filter { !(it == '[' || it == ']') }.map { it }.joinToString("").split(',').map { it.toInt() }

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