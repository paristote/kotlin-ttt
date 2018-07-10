package fr.aristote.ttt.feature

enum class CellStatus(private val symbol: String) {
    CROSS("X"),
    DOT("O"),
    EMPTY(" ");

    override fun toString(): String {
        return symbol
    }
}
