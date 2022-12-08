package solutions.day7nospaceleftondevice.model

class ChangeDirectoryCommand(private val argument : String) : Command {
    override fun execute(c: Component): Component? {
        val nextFolderIMovedIn = when (argument) {
            ".." -> c.parent
            "/" -> c.getRoot()
            else -> c.getChildrenFolderByName(argument)

        }
        return nextFolderIMovedIn
    }

    override fun toString(): String {
        return when (argument) {
            ".." -> "Going back to parent folder"
            "/" -> "Going back to root"
            else -> "Entering in folder $argument"

        }
    }


    companion object {
        fun parseLine(line : String) : ChangeDirectoryCommand {
            return ChangeDirectoryCommand(line.split(" ").last())
        }
    }
}