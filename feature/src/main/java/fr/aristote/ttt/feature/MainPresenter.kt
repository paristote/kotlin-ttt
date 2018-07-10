package fr.aristote.ttt.feature

import android.util.Log

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    private val game = GameService()

    override fun onCellPressed(cell: Cell) {
        val playAttempt = game.play(cell)
        when (playAttempt.result) {
            Play.Result.NEXT -> finishPlay(playAttempt)
            Play.Result.PLAY_AGAIN -> playAgain()
        }
    }

    override fun onGameFinished() {
        view.resetBoard()
        game.newGame()
    }

    override fun onSwitchPlayers() {
        view.resetBoard()
        game.startingPlayer = Player.switch(game.startingPlayer)
        game.newGame()
    }

    private fun finishPlay(play: Play) {
        logGameStatus()
        updateCell(play)
        val gameStatus = game.checkGameStatus()
        when (gameStatus) {
            GameStatus.CROSS_WINS, GameStatus.DOT_WINS ->
                view.showResult("CONGRATULATIONS PLAYER ${play.player}, YOU WON !")
            GameStatus.DRAW ->
                view.showResult("IT'S A DRAW ¯\\_(ツ)_/¯")
            GameStatus.CONTINUE -> {
                // nothing to do
            }
        }
    }

    private fun updateCell(play: Play) {
        val cellStatus = when (play.player) {
            Player.CROSS -> CellStatus.CROSS
            Player.DOT -> CellStatus.DOT
        }
        view.updateCell(play.cell, cellStatus)
    }

    private fun playAgain() {
        view.showToast("PLAY AGAIN")
    }

    private fun logGameStatus() {
        Log.d("GameStatus", "GameStatus")
        when (game.checkGameStatus()) {
            GameStatus.CROSS_WINS -> Log.d("GameStatus", "CROSS WINS :)")
            GameStatus.DOT_WINS -> Log.d("GameStatus", "DOT WINS :)")
            GameStatus.DRAW -> Log.d("GameStatus", "DRAW :S")
            GameStatus.CONTINUE -> Log.d("GameStatus", "NEXT PLAYER")
        }
        var line = "\n"
        for (i in 0..2) {
            line += "| "
            for (j in 0..2) {
                line += when (game.getBoard().getCellStatusAt(i, j)) {
                    CellStatus.DOT -> "o | "
                    CellStatus.CROSS -> "x | "
                    CellStatus.EMPTY -> "  | "
                }
            }
            line += "\n"
        }
        Log.d("GameStatus", line)
    }
}
