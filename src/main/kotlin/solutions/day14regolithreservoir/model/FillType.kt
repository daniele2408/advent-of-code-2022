package solutions.day14regolithreservoir.model

enum class FillType(val symbol: String) {
    AIR("."),
    ROCK("#"),
    SAND("o"),
    SOURCE("+"),
    VOID(" ")
    ;
}