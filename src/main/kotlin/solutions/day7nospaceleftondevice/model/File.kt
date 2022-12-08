package solutions.day7nospaceleftondevice.model

class File(private val size : Int, override val name : String, override val parent: Folder) : Component {

    override val children: MutableList<Component>? = null

    override fun getTotalSize(): Int {
        return size
    }

    override fun getRoot(): Folder {
        return parent.getRoot()
    }

    override fun getChildrenFolderByName(name: String): Folder? {
        return null
    }

    override fun toString(): String {
        return "$name (file, size=${getTotalSize()})"
    }


}