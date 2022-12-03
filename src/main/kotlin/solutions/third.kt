package solutions

import retrieveRowsFromFile

val charPriorityMap = ((('a' .. 'z') + ('A' .. 'Z')) zip 1..52).associate { (char, prior) -> char to prior }

fun sumPrioritiesItemsRucksack() : Int {
    val rows: List<String> = retrieveRowsFromFile("inputday3.txt")

    return rows.map { splitRowInListOfTwo(it) }
        .sumOf { charPriorityMap[findCommonLetter(it)] ?: 0 }

}

fun sumPrioritiesItemsGroupOfThree() : Int {
    val rowsChunked: List<List<String>> = retrieveRowsFromFile("inputday3.txt").chunked(3)

    return rowsChunked.sumOf { charPriorityMap[findCommonLetter(it)] ?: 0 }

}

fun splitRowInListOfTwo(row : String) : List<String> {
    val rowLength = row.length
    assert(rowLength % 2 == 0)

    val halfLength = rowLength / 2

    return listOf(row.slice(IntRange(0, halfLength-1)), row.slice(IntRange(halfLength, rowLength-1)))
}

fun findCommonLetter(stringList: List<String>) : Char {
    return stringList.map { it.toSet() }.reduce { acc, set -> acc.intersect(set) }.first()
}
