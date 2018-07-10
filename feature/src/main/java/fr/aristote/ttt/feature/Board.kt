package fr.aristote.ttt.feature

class Board {

    companion object {
        private const val ROWS = 3
        private const val COLS = 3
    }

    val cells = Array(ROWS) { _ -> Array(COLS) { _ -> CellStatus.EMPTY } }

    fun isBoardFull(): Boolean {
        for (row in 0 until ROWS) {
            for (col in 0 until COLS) {
                if (getCellStatusAt(row, col) == CellStatus.EMPTY) {
                    return false
                }
            }
        }
        return true
    }

    fun getCellStatusAt(row: Int, col: Int): CellStatus {
        return cells[row][col]
    }

    fun getCellStatus(cell: Cell): CellStatus {
        return when (cell) {
            Cell.TOP_LEFT -> cells[0][0]
            Cell.TOP_CENTER -> cells[0][1]
            Cell.TOP_RIGHT -> cells[0][2]
            Cell.CENTER_LEFT -> cells[1][0]
            Cell.MIDDLE -> cells[1][1]
            Cell.CENTER_RIGHT -> cells[1][2]
            Cell.BOTTOM_LEFT -> cells[2][0]
            Cell.BOTTOM_CENTER -> cells[2][1]
            Cell.BOTTOM_RIGHT -> cells[2][2]
        }
    }

    fun setCellStatus(cell: Cell, status: CellStatus) {
        when (cell) {
            Cell.TOP_LEFT -> cells[0][0] = status
            Cell.TOP_CENTER -> cells[0][1] = status
            Cell.TOP_RIGHT -> cells[0][2] = status
            Cell.CENTER_LEFT -> cells[1][0] = status
            Cell.MIDDLE -> cells[1][1] = status
            Cell.CENTER_RIGHT -> cells[1][2] = status
            Cell.BOTTOM_LEFT -> cells[2][0] = status
            Cell.BOTTOM_CENTER -> cells[2][1] = status
            Cell.BOTTOM_RIGHT -> cells[2][2] = status
        }
    }
}
