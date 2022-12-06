package model

enum class ProtocolMarker(val distinctCharacters: Int) {
    START_OF_PACKET(4),
    START_OF_MESSAGE(14)
}