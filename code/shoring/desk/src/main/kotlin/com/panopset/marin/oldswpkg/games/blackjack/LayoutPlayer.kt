package com.panopset.marin.oldswpkg.games.blackjack

import com.panopset.blackjackEngine.BlackjackGameState
import com.panopset.compat.Tile

class LayoutPlayer(tile: Tile?, cardDim: IntArray?, cs: BlackjackGameState?, chipDim: IntArray) :
    Layout(tile!!, cardDim!!, cs!!) {
    val chipW: Int
    val chipH: Int
    var playerChipY: Int? = null
        get() {
            if (field == null) {
                field = t.top + cardH
            }
            return field
        }
        private set
    var playerMsgY: Int? = null
        get() {
            if (field == null) {
                field = playerChipY!! + chipH * 2
            }
            return field
        }
        private set
    var playerCardXstart: Int? = null
        get() {
            if (field == null) {
                var cardsWidth = 0
                for (p in cs.players) {
                    for (h in p.hands) {
                        if (h.getBlackjackCards().isNotEmpty()) {
                            cardsWidth += cardW
                            cardsWidth += cardSpacer * h.getBlackjackCards().size
                        }
                    }
                }
                field = centerW - cardsWidth / 2
            }
            return field
        }
        private set
    var playerY: Int? = null
        get() {
            if (field == null) {
                field = t.top + cardH
            }
            return field
        }
        private set

    init {
        chipW = chipDim[0]
        chipH = chipDim[1]
    }
}
