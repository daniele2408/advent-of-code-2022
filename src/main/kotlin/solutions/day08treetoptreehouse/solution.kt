package solutions.day08treetoptreehouse

import solutions.day08treetoptreehouse.model.Forest
import retrieveRowsFromFile

fun countVisibleTrees() : Int {

    val rows: List<String> = retrieveRowsFromFile("inputday08.txt")
    val forest : Forest = Forest.fromList(rows)

    return forest.countVisibleTrees()
}

fun findBestScenicScore() : Int {
    val rows: List<String> = retrieveRowsFromFile("inputday08.txt")
    val forest : Forest = Forest.fromList(rows)

    return forest.getBestScenicScore()
}