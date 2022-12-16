package solutions.day13distresssignal.logic

import solutions.day13distresssignal.model.Packet

class Parser {

    companion object {

        // 1 rimuovi bracket esterni
        // rimuovi elementi prima di una nuova prima quadra aperta
        // rimuovi elementi dopo dell'ultima eventuale quadra chiusa

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

//        fun parseNestedListString(acc : MutableList<Int>, rest: String) : MutableList<Int> {
//            if (rest.isEmpty()) return acc
//
//            val parsedListString : String = parseLastInternalBrackets(rest)
//
//            val resultsList : MutableList<Int> = parsedListString.trim('[', ']')
//                .split(',')
//                .map { it.toInt() }.toMutableList()
//
//            acc.add(resultsList)
//
//            return parseNestedListString(acc, rest.replace(parsedListString, "").replace("[,","["))
//
//        }

        fun from(s: String) {

        }

    }

}