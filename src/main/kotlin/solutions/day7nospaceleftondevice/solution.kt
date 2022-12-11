package solutions.day7nospaceleftondevice

import solutions.day7nospaceleftondevice.logic.TreeFactory
import solutions.day7nospaceleftondevice.logic.TreeObserver
import retrieveRowsFromFile

fun getTotalSizeDirectories() : Int {

    val rows : List<String> = retrieveRowsFromFile("inputday07.txt")

    val treeObserver = TreeObserver()
    TreeFactory.parseLines(rows, treeObserver)

    return treeObserver.getTotalSizeFoldersSmallerThan(100000)

}


fun getSizeFolderToDelete() : Int {

    val rows : List<String> = retrieveRowsFromFile("inputday07.txt")

    val treeObserver = TreeObserver()
    val tree = TreeFactory.parseLines(rows, treeObserver)

    return treeObserver.getSmallestFolderSizeToSaveSpace(30000000 - (70000000 - tree.getTotalSize()))

}