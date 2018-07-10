package fr.aristote.ttt.feature

interface MainContract {

    interface View {

        fun updateCell(cell: Cell, cellStatus: CellStatus)

        fun showResult(resultMessage: String)

        fun showToast(message: String)

        fun resetBoard()
    }

    interface Presenter {

        fun onCellPressed(cell: Cell)

        fun onGameFinished()

        fun onSwitchPlayers()
    }
}
