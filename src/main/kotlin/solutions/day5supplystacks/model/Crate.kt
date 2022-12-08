package solutions.day5supplystacks.model

class Crate(input: String) {

    private val content : String

    init {
        content = input.trim('[', ']')
    }

    override fun toString(): String {
        return content
    }

    companion object {
        fun from(char: Char) : Crate {
            return Crate(char.toString())
        }
    }


}