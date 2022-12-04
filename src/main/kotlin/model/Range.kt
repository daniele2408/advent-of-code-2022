package model

class Range(val start: Int, val end: Int) {

    fun fullyOverlaps(other: Range) : Boolean {
        return (this.start <= other.start && this.end >= other.end) ||
                (this.start >= other.start && this.end <= other.end)
    }

    fun partialOverlaps(other: Range) : Boolean {
        return !((this.end < other.start) || (this.start > other.end))
    }

    companion object {

        fun from(s: String) : Range {
            val splittedValues = s.split("-")
            return Range(splittedValues[0].toInt(), splittedValues[1].toInt())
        }

    }
}