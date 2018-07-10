package fr.aristote.ttt.feature

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class GameServiceTest {

    private lateinit var service: GameService

    @Before
    fun createObjectUnderTest() {
        service = GameService()
    }

    @Test
    fun newGame_createsNewBoard() {
        // arrange
        val firstBoard = service.getBoard()

        // act
        service.newGame()

        // assert
        assertNotEquals(firstBoard, service.getBoard())
    }

    @Test
    fun play_onEmptyCell_onEmptyBoard_returnsCorrectPlay() {
        // arrange
        val playedCell = Cell.TOP_LEFT

        // act
        val play = service.play(playedCell)

        // assert
        assertEquals(Player.CROSS, play.player)
        assertEquals(playedCell, play.cell)
        assertEquals(Play.Result.NEXT, play.result)
    }

    @Test
    fun play_onUsedCell_returnsCorrectPlayResult() {
        // arrange
        val playedCell = Cell.TOP_LEFT
        val board = Board()
        board.setCellStatus(playedCell, CellStatus.DOT)
        service = GameService(board)

        // act
        val play = service.play(playedCell)

        // assert
        assertEquals(Play.Result.PLAY_AGAIN, play.result)
    }

    @Test
    fun play_withDefaultPlayer_switchesPlayer() {
        // act
        service.play(Cell.TOP_LEFT)

        // assert
        assertEquals(Player.DOT, service.currentPlayer)
    }

    @Test
    fun play_withOtherPlayer_switchesPlayer() {
        // arrange
        // play once to change player
        service.play(Cell.TOP_LEFT)

        // act
        // play again to switch back to the default player
        service.play(Cell.TOP_CENTER)

        // assert
        assertEquals(Player.CROSS, service.currentPlayer)
    }

    @Test
    fun getCurrentPlayer_returnsCurrentPlayer() {
        // arrange
        // play once to switch the current player to DOT
        service.play(Cell.CENTER_LEFT)

        // act and assert
        assertEquals(Player.DOT, service.currentPlayer)
    }

    @Test
    fun getStartingPlayer_returnsStartingPlayer() {
        // arrange
        // play once to switch the current player to DOT
        service.play(Cell.CENTER_LEFT)

        // act and assert
        // the starting player should remain as CROSS
        assertEquals(Player.CROSS, service.startingPlayer)
    }

    @Test
    fun checkGameStatus_withEmptyBoard_returnsContinue() {
        // act and assert
        assertEquals(GameStatus.CONTINUE, service.checkGameStatus())
    }

    @Test
    fun checkGameStatus_withFullBoard_returnsDraw() {
        // arrange
        val board = Board()
        board.setCellStatus(Cell.TOP_LEFT, CellStatus.DOT)
        board.setCellStatus(Cell.TOP_CENTER, CellStatus.CROSS)
        board.setCellStatus(Cell.TOP_RIGHT, CellStatus.CROSS)
        board.setCellStatus(Cell.CENTER_LEFT, CellStatus.CROSS)
        board.setCellStatus(Cell.MIDDLE, CellStatus.CROSS)
        board.setCellStatus(Cell.CENTER_RIGHT, CellStatus.DOT)
        board.setCellStatus(Cell.BOTTOM_LEFT, CellStatus.DOT)
        board.setCellStatus(Cell.BOTTOM_CENTER, CellStatus.DOT)
        board.setCellStatus(Cell.BOTTOM_RIGHT, CellStatus.CROSS)
        service = GameService(board)

        // act and assert
        assertEquals(GameStatus.DRAW, service.checkGameStatus())
    }

    @Test
    fun checkGameStatus_withOneDot_returnsContinue() {
        // arrange
        val board = Board()
        board.setCellStatus(Cell.TOP_LEFT, CellStatus.DOT)
        service = GameService(board)

        // act and assert
        assertEquals(GameStatus.CONTINUE, service.checkGameStatus())
    }

    @Test
    fun checkGameStatus_withThreeDotsNotInRow_returnsContinue() {
        // arrange
        val board = Board()
        board.setCellStatus(Cell.TOP_LEFT, CellStatus.DOT)
        board.setCellStatus(Cell.TOP_CENTER, CellStatus.DOT)
        board.setCellStatus(Cell.MIDDLE, CellStatus.DOT)
        service = GameService(board)

        // act and assert
        assertEquals(GameStatus.CONTINUE, service.checkGameStatus())
    }

    @Test
    fun checkGameStatus_withRowOfThreeDots_returnsDotWins() {
        // arrange
        val board = Board()
        board.setCellStatus(Cell.TOP_LEFT, CellStatus.DOT)
        board.setCellStatus(Cell.TOP_CENTER, CellStatus.DOT)
        board.setCellStatus(Cell.TOP_RIGHT, CellStatus.DOT)
        service = GameService(board)

        // act and assert
        assertEquals(GameStatus.DOT_WINS, service.checkGameStatus())
    }

    @Test
    fun checkGameStatus_withColumnOfThreeDots_returnsDotWins() {
        // arrange
        val board = Board()
        board.setCellStatus(Cell.TOP_LEFT, CellStatus.DOT)
        board.setCellStatus(Cell.CENTER_LEFT, CellStatus.DOT)
        board.setCellStatus(Cell.BOTTOM_LEFT, CellStatus.DOT)
        service = GameService(board)

        // act and assert
        assertEquals(GameStatus.DOT_WINS, service.checkGameStatus())
    }

    @Test
    fun checkGameStatus_withDiagonalOfThreeCrosses_returnsCrossWins() {
        // arrange
        val board = Board()
        board.setCellStatus(Cell.TOP_LEFT, CellStatus.CROSS)
        board.setCellStatus(Cell.MIDDLE, CellStatus.CROSS)
        board.setCellStatus(Cell.BOTTOM_RIGHT, CellStatus.CROSS)
        service = GameService(board)

        // act and assert
        assertEquals(GameStatus.CROSS_WINS, service.checkGameStatus())
    }

    @Test
    fun checkGameStatus_withOtherDiagonalOfThreeCrosses_returnsCrossWins() {
        // arrange
        val board = Board()
        board.setCellStatus(Cell.TOP_RIGHT, CellStatus.CROSS)
        board.setCellStatus(Cell.MIDDLE, CellStatus.CROSS)
        board.setCellStatus(Cell.BOTTOM_LEFT, CellStatus.CROSS)
        service = GameService(board)

        // act and assert
        assertEquals(GameStatus.CROSS_WINS, service.checkGameStatus())
    }
}