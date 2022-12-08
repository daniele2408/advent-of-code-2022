package solutions.day7nospaceleftondevice.model

interface Component {
    val name : String
    val parent : Component?
    val children : MutableList<Component>?

    fun getTotalSize() : Int
    fun getRoot() : Folder
    fun getChildrenFolderByName(name : String) : Folder?

}