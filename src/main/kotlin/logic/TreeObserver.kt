package logic

import model.File
import model.Folder
import kotlin.streams.toList

class TreeObserver {
    private val folders : MutableSet<Folder> = mutableSetOf()
    private val files : MutableSet<File> = mutableSetOf()


    fun annotateFolder(folder: Folder) {
        folders.add(folder)
    }

    fun annotateFile(file: File) {
        files.add(file)
    }

    fun getTotalSizeFiles() : Int {
        return files.sumOf { it.getTotalSize() }
    }

    fun getTotalSizeFoldersSmallerThan(maxSize : Int) : Int {
        return folders.stream().filter { it.getTotalSize() <= maxSize }.map { it.getTotalSize() }.toList().sum()
    }

    fun getSmallestFolderSizeToSaveSpace(spaceToFree : Int) : Int {
        val folderToFree : Folder? = folders.stream().sorted(Comparator.comparing { it.getTotalSize() })
            .dropWhile { it.getTotalSize() < spaceToFree }.findFirst().orElse(null)
        return folderToFree?.getTotalSize() ?: 0
    }
}