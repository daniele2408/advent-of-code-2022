package solutions.day9ropebridge.model

data class MoveAction(val moveType : Move, val steps : Int) {

    companion object {
        fun from(s : String, steps: Int) : MoveAction {
            return MoveAction(Move.fromSymbol(s), steps)
        }

        fun from(s : String, steps: String) : MoveAction {
            return MoveAction(Move.fromSymbol(s), steps.toInt())
        }
    }

}