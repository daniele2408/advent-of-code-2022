fun getResourceAsText(path: String): String? =
    object {}.javaClass.getResource(path)?.readText()

fun retrieveRowsFromFile(filePath: String): List<String> {
    val contentMatches = getResourceAsText(filePath)
    return contentMatches?.split("\n") ?: emptyList()
}