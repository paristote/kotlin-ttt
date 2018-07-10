package fr.aristote.ttt.feature

class GameService() {

    companion object {
        val DEFAULT_PLAYER = Player.CROSS
    }

    var startingPlayer = DEFAULT_PLAYER
    var currentPlayer = startingPlayer
        private set
    private var board = Board()

    constructor(newBoard: Board) : this() {
        board = newBoard
    }

    fun newGame() {
        board = Board()
        currentPlayer = startingPlayer
    }

    fun getBoard(): Board {
        return board
    }

    fun play(cell: Cell): Play {
        if (board.getCellStatus(cell) == CellStatus.EMPTY) {
            when (currentPlayer) {
                Player.CROSS -> board.setCellStatus(cell, CellStatus.CROSS)
                Player.DOT -> board.setCellStatus(cell, CellStatus.DOT)
            }
            val play = Play(currentPlayer, cell, Play.Result.NEXT)
            currentPlayer = Player.switch(currentPlayer)
            return play
        }
        return Play(currentPlayer, cell, Play.Result.PLAY_AGAIN)
    }

    fun checkGameStatus(): GameStatus {
        // check diagonal \
        var boardStatus = checkDiagonalTopLeftToDownRight(board)
        if (boardStatus != GameStatus.CONTINUE) {
            return boardStatus
        }

        // check diagonal /
        boardStatus = checkDiagonalTopRightToDownLeft(board)
        if (boardStatus != GameStatus.CONTINUE) {
            return boardStatus
        }

        for (i in 0..2) {
            // check rows
            boardStatus = checkRow(board, i)
            if (boardStatus != GameStatus.CONTINUE) {
                return boardStatus
            }
            // check columns
            boardStatus = checkColumn(board, i)
            if (boardStatus != GameStatus.CONTINUE) {
                return boardStatus
            }
        }

        // check whether the board is full
        return if (board.isBoardFull()) {
            GameStatus.DRAW
        } else {
            GameStatus.CONTINUE
        }
    }

    private fun checkRow(board: Board, row: Int): GameStatus {
        return if (board.getCellStatusAt(row, 0) == board.getCellStatusAt(row, 1) &&
                board.getCellStatusAt(row, 1) == board.getCellStatusAt(row, 2)) {
            extrapolateCellStatus(board.getCellStatusAt(row, 0))
        } else {
            GameStatus.CONTINUE
        }
    }

    private fun checkColumn(board: Board, column: Int): GameStatus {
        return if (board.getCellStatusAt(0, column) == board.getCellStatusAt(1, column) &&
                board.getCellStatusAt(1, column) == board.getCellStatusAt(2, column)) {
            extrapolateCellStatus(board.getCellStatusAt(0, column))
        } else {
            GameStatus.CONTINUE
        }
    }

    private fun checkDiagonalTopLeftToDownRight(board: Board): GameStatus {
        return if (board.getCellStatusAt(0, 0) == board.getCellStatusAt(1, 1) &&
                board.getCellStatusAt(1, 1) == board.getCellStatusAt(2, 2)) {
            extrapolateCellStatus(board.getCellStatusAt(1, 1))
        } else {
            GameStatus.CONTINUE
        }
    }

    private fun checkDiagonalTopRightToDownLeft(board: Board): GameStatus {
        return if (board.getCellStatusAt(0, 2) == board.getCellStatusAt(1, 1) &&
                board.getCellStatusAt(1, 1) == board.getCellStatusAt(2, 0)) {
            extrapolateCellStatus(board.getCellStatusAt(1, 1))
        } else {
            GameStatus.CONTINUE
        }
    }

    private fun extrapolateCellStatus(cellStatus: CellStatus): GameStatus {
        return when (cellStatus) {
            CellStatus.CROSS -> GameStatus.CROSS_WINS
            CellStatus.DOT -> GameStatus.DOT_WINS
            else -> {
                GameStatus.CONTINUE
            }
        }
    }
}
