package solutions.day7nospaceleftondevice.model

interface Command {
    fun execute(c : Component) : Component?
}