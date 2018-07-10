package fr.aristote.ttt.feature

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BoardTest {

    @Test
    fun setCellStatus() {
        // arrange
        val board = Board()

        // act
        board.setCellStatus(Cell.TOP_LEFT, CellStatus.CROSS)

        // assert
        assertEquals(CellStatus.CROSS, board.cells[0][0])
    }

    @Test
    fun isBoardFull_withBoardEmpty_returnsFalse() {
        // arrange
        val board = Board()

        // act and assert
        assertFalse(board.isBoardFull())
    }

    @Test
    fun isBoardFull_withBoardNotFull_returnsFalse() {
        // arrange
        val board = Board()
        board.setCellStatus(Cell.TOP_LEFT, CellStatus.DOT)
        board.setCellStatus(Cell.BOTTOM_RIGHT, CellStatus.CROSS)

        // act and assert
        assertFalse(board.isBoardFull())
    }

    @Test
    fun isBoardFull_withBoardFull_returnsTrue() {
        // arrange
        val board = Board()
        board.setCellStatus(Cell.TOP_LEFT, CellStatus.DOT)
        board.setCellStatus(Cell.TOP_CENTER, CellStatus.CROSS)
        board.setCellStatus(Cell.TOP_RIGHT, CellStatus.DOT)
        board.setCellStatus(Cell.CENTER_LEFT, CellStatus.CROSS)
        board.setCellStatus(Cell.MIDDLE, CellStatus.DOT)
        board.setCellStatus(Cell.CENTER_RIGHT, CellStatus.CROSS)
        board.setCellStatus(Cell.BOTTOM_LEFT, CellStatus.DOT)
        board.setCellStatus(Cell.BOTTOM_CENTER, CellStatus.CROSS)
        board.setCellStatus(Cell.BOTTOM_RIGHT, CellStatus.DOT)

        // act and assert
        assertTrue(board.isBoardFull())
    }
}