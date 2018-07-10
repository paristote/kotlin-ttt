package fr.aristote.ttt.feature

data class Play(val player: Player, val cell: Cell, val result: Result) {
    enum class Result {
        NEXT,
        PLAY_AGAIN
    }
}
