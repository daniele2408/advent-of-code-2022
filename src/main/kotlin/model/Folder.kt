package model

class Folder(override val parent: Folder?, override val name: String) : Component {

    private constructor(name : String) : this(null, name)

    override val children : MutableList<Component> = mutableListOf()
    override fun getTotalSize() : Int {
        return children.fold(0) {acc, e -> acc + e.getTotalSize()}
    }

    override fun getChildrenFolderByName(name: String) : Folder? {
        return children.filterIsInstance<Folder>().find { it.name == name }
    }
    override fun getRoot() : Folder {
        if (parent == null) return this
        return parent.getRoot()

    }

    fun addComponent(component: Component) {
        children.add(component)
    }

    override fun toString(): String {
        return "$name (dir, size=${getTotalSize()})"
    }


    companion object {
        fun initRootFolder() : Folder {
            return Folder("/")
        }
    }
}