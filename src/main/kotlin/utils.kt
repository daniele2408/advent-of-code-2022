fun getResourceAsText(path: String): String? =
    object {}.javaClass.getResource(path)?.readText()

fun retrieveRowsFromFile(filePath: String): List<String> {
    val contentMatches = getResourceAsText(filePath)
    return contentMatches?.split("\n") ?: emptyList()
}

// thanks to https://jivimberg.io/blog/2018/06/02/implementing-takewhileinclusive-in-kotlin/ !
fun <T> List<T>.takeWhileInclusive(pred: (T) -> Boolean): List<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = pred(it)
        result
    }
}