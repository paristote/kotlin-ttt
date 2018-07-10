package fr.aristote.ttt.feature

enum class Player {
    CROSS,
    DOT;

    companion object {
        fun switch(player: Player): Player {
            return when (player) {
                Player.CROSS -> Player.DOT
                Player.DOT -> Player.CROSS
            }
        }
    }
}
