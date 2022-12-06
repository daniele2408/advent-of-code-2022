package solutions

import logic.findStartOfPacketMarker
import model.ProtocolMarker
import retrieveRowsFromFile

fun findMarkerPosition(markerType: ProtocolMarker): Int {
    val message: String = retrieveRowsFromFile("inputday6.txt")[0]
    return findStartOfPacketMarker(message, markerType.distinctCharacters)
}