package com.panopset.blackjackEngine

class BlackjackCard(val card: Card) {

    constructor(card: Card, isShowing: Boolean) : this(card) {
        card.isShowing = isShowing
    }

    constructor(cardDefinition: CardDefinition) : this(Card(cardDefinition))
    constructor(blackjackCard: BlackjackCard) : this(blackjackCard.card, blackjackCard.card.isShowing)

    fun isAce(): Boolean {
        return card.face.offset == 0
    }

    fun getSoftValue(): Int {
        return if (isAce()) {
            11
        } else getNonAceValue()
    }

    fun getHardValue(): Int {
        return if (isAce()) {
            1
        } else getNonAceValue()
    }

    private fun getNonAceValue(): Int {
        return if (card.face.offset < 10) {
            card.face.offset + 1
        } else {
            10
        }
    }

    override fun toString(): String {
        return card.name()
    }

    fun show() {
        card.isShowing = true
    }
}
