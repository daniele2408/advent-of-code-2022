package solutions.day06tuningtrouble.logic

fun findStartOfPacketMarker(message: String, distinctCharacters: Int) : Int {
    val messageLength = message.length
    return (0 until messageLength - distinctCharacters)
        .dropWhile { message.subSequence(it, it + distinctCharacters).toList()
            .distinct().count() != distinctCharacters }.first() + distinctCharacters
}