package solutions
import model.Range
import retrieveRowsFromFile

fun countTotallyOverlappingRanges() : Int {
    val rows: List<String> = retrieveRowsFromFile("inputday4.txt")
    return rows.map { it.split(",").subList(0, 2) }
        .count { (first, second) -> Range.from(first).fullyOverlaps(Range.from(second)) }
}

fun countPartiallyOverlappingRanges() : Int {
    val rows: List<String> = retrieveRowsFromFile("inputday4.txt")
    return rows.map { it.split(",").subList(0, 2) }
        .count { (first, second) -> Range.from(first).partialOverlaps(Range.from(second)) }
}
