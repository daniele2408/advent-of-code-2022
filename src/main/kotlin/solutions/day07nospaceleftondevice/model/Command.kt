package solutions.day07nospaceleftondevice.model

interface Command {
    fun execute(c : Component) : Component?
}