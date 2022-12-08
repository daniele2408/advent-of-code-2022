package solutions.day8treetoptreehouse

import solutions.day8treetoptreehouse.model.Forest
import retrieveRowsFromFile

fun countVisibleTrees() : Int {

    val rows: List<String> = retrieveRowsFromFile("inputday8.txt")
    val forest : Forest = Forest.fromList(rows)

    return forest.countVisibleTrees()
}

fun findBestScenicScore() : Int {
    val rows: List<String> = retrieveRowsFromFile("inputday8.txt")
    val forest : Forest = Forest.fromList(rows)

    return forest.getBestScenicScore()
}