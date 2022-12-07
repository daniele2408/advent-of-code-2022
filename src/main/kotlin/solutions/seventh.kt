package solutions

import logic.TreeFactory
import logic.TreeObserver
import retrieveRowsFromFile

fun getTotalSizeDirectories() : Int {

    val rows : List<String> = retrieveRowsFromFile("inputday7.txt")

    val treeObserver = TreeObserver()
    TreeFactory.parseLines(rows, treeObserver)

    return treeObserver.getTotalSizeFoldersSmallerThan(100000)

}


fun getSizeFolderToDelete() : Int {

    val rows : List<String> = retrieveRowsFromFile("inputday7.txt")

    val treeObserver = TreeObserver()
    val tree = TreeFactory.parseLines(rows, treeObserver)

    return treeObserver.getSmallestFolderSizeToSaveSpace(30000000 - (70000000 - tree.getTotalSize()))

}