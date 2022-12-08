package solutions.day7nospaceleftondevice.model

class CreateDirectoryCommand(private val argument : String) : Command {
    override fun execute(c: Component): Component? {
        if (c is Folder) {
            val newFolder = Folder(c, argument)
            c.addComponent(newFolder)
            return newFolder
        }
        return null
    }

    override fun toString(): String {
        return "Create Folder '$argument'"
    }


    companion object {
        fun fromLine(line : String) : Command {
            return CreateDirectoryCommand(line.split(" ").last())
        }
    }

}