package solutions.day4campcleanup
import solutions.day4campcleanup.model.Range
import retrieveRowsFromFile

fun countTotallyOverlappingRanges() : Int {
    val rows: List<String> = retrieveRowsFromFile("inputday04.txt")
    return rows.map { it.split(",").subList(0, 2) }
        .count { (first, second) -> Range.from(first).fullyOverlaps(Range.from(second)) }
}

fun countPartiallyOverlappingRanges() : Int {
    val rows: List<String> = retrieveRowsFromFile("inputday04.txt")
    return rows.map { it.split(",").subList(0, 2) }
        .count { (first, second) -> Range.from(first).partialOverlaps(Range.from(second)) }
}
