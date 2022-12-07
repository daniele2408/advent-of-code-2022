package model

interface Command {
    fun execute(c : Component) : Component?
}