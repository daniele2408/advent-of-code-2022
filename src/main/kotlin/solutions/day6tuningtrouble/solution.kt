package solutions.day6tuningtrouble

import solutions.day6tuningtrouble.logic.findStartOfPacketMarker
import solutions.day6tuningtrouble.model.ProtocolMarker
import retrieveRowsFromFile

fun findMarkerPosition(markerType: ProtocolMarker): Int {
    val message: String = retrieveRowsFromFile("inputday06.txt")[0]
    return findStartOfPacketMarker(message, markerType.distinctCharacters)
}