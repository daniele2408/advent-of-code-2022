package solutions.day01caloriecounting

import getResourceAsText

fun getCaloriesTopNElves(n : Int) : Int {
    val content = getResourceAsText("inputday01.txt")
    val strings : List<String> = content?.replace("\n\n", " ")?.split(" ") ?: emptyList()
    val listBags : List<List<Int>> = strings.map { it.split("\n").map { n -> n.toInt() } }.toList()
    return listBags.sortedBy { it.sum() }.reversed().take(n).flatten().sum()
}