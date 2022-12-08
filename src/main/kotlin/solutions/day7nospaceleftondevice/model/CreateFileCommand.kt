package solutions.day7nospaceleftondevice.model

class CreateFileCommand(val size: Int, val name: String) : Command {
    override fun execute(c: Component): Component? {
        if (c is Folder) {
            val newFile = File(size, name, c)
            c.addComponent(newFile)
            return newFile
        }
        return null
    }

    override fun toString(): String {
        return "Create File '$name' (${size} B)"
    }

    companion object {
        fun fromLine(line : String) : Command {
            val (size, name) = line.split(" ")
            return CreateFileCommand(size.toInt(), name)
        }
    }



}