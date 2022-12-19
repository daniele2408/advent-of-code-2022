package solutions.day06tuningtrouble

import solutions.day06tuningtrouble.logic.findStartOfPacketMarker
import solutions.day06tuningtrouble.model.ProtocolMarker
import retrieveRowsFromFile

fun findMarkerPosition(markerType: ProtocolMarker): Int {
    val message: String = retrieveRowsFromFile("inputday06.txt")[0]
    return findStartOfPacketMarker(message, markerType.distinctCharacters)
}