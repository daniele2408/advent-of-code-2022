package solutions.day7nospaceleftondevice.logic

import solutions.day7nospaceleftondevice.model.*

object TreeFactory {

    fun parseLines(lines: List<String>, treeObserver: TreeObserver) : Folder {
        val resultCommands = turnLinesInCommands(listOf(), lines.subList(1, lines.size))
        return create(resultCommands, treeObserver)
    }

    private fun create(commands: List<Command>, treeObserver: TreeObserver) : Folder {
        var currentFolder : Folder = Folder.initRootFolder()
        commands.forEach {
//            println(it)
            val nextComponent : Component? = it.execute(currentFolder)
            if (it is CreateDirectoryCommand) {
                treeObserver.annotateFolder(nextComponent as Folder)
            }
            if (it is CreateFileCommand) {
                treeObserver.annotateFile(nextComponent as File)
            }
            if (nextComponent != null && it is ChangeDirectoryCommand) {
                currentFolder = nextComponent as Folder
            }
        }
        return currentFolder.getRoot()
    }

    private fun turnLinesInCommands(acc : List<Command>, rest : List<String>) : List<Command> {
        if (rest.isEmpty()) return acc
        if (rest.first().startsWith("$ cd")) {
            val cdCommands = rest.takeWhile { it.startsWith("$ cd") }
            return turnLinesInCommands(
                acc + cdCommands.map { ChangeDirectoryCommand.parseLine(it) }.toList(),
                rest.subList(cdCommands.size, rest.size)
            )
        }
        val lsCommands: List<String> = rest.takeWhile { !it.startsWith("$ cd") }
        return turnLinesInCommands(
            acc +
                    lsCommands.filter { it.startsWith("dir") }.map { CreateDirectoryCommand.fromLine(it) } +
                    lsCommands.filter { !it.startsWith("$") && !it.startsWith("dir") }.map { CreateFileCommand.fromLine(it) }
            , rest.subList(lsCommands.size, rest.size))
    }

}