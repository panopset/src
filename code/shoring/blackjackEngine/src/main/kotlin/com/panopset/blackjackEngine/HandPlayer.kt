package com.panopset.blackjackEngine

import com.panopset.compat.Logz

class HandPlayer(val wager: Wager) {
    private val hand = Hand()
    var isSplit = false
    var isSurrendered = false
    var isSelectedEvenMoney = false
    var action: String = CMD_DEAL

    constructor(handPlayer: HandPlayer): this(Wager(handPlayer.wager)) {
        isSplit = handPlayer.isSplit
        isSurrendered = handPlayer.isSurrendered
        isSelectedEvenMoney = handPlayer.isSelectedEvenMoney
        hand.isFinal = handPlayer.hand.isFinal
        action = handPlayer.action
        message = handPlayer.message
        val iterator = handPlayer.hand.getIterator()
        val cardsToAddToHand = ArrayList<BlackjackCard>()
        while (iterator.hasNext()) {
            val card = iterator.next()
            cardsToAddToHand.add(card)
        }
        for (card in cardsToAddToHand) {
            dealCard(card)
        }
    }

    fun removeSecondCard(): BlackjackCard {
        return hand.cards.removeAt(1)
    }

    fun isNatural21(): Boolean {
        return if (isSplit) {
            false
        } else hand.isNatural21()
    }

    fun setSplit() {
        isSplit = true
    }

    var message: String = promptDeal
    fun dealCard(card: BlackjackCard) {
        hand.dealCard(card)
        message = ""
    }

//    var message: String
//        get() {
//            if (msg.isEmpty()) {
//                msg = "Please select deal (L)"
//            }
//            return msg
//        }
//        set(message) {
//            msg = message
//        }

    fun canDouble(configIsDoubleAfterSplitAllowed: Boolean): Boolean {
        if (!hand.isInitialDeal) {
            return false
        }
        return if (isSplit) {
            configIsDoubleAfterSplitAllowed
        } else {
            true
        }
    }

    fun surrender() {
        isSurrendered = true
        hand.stand()
    }

    fun isBustedOrSurrenderred(): Boolean {
        return if (hand.isBusted()) {
            true
        } else {
            isSurrendered
        }
    }

    fun standWithEvenMoney() {
        hand.stand()
        isSelectedEvenMoney = true
    }

    val isCardFacesSplittable: Boolean
        get() = isCardFacesSplittable(false)
    val isCardFacesSplittableIncludeMessage: Boolean
        get() = isCardFacesSplittable(true)

    private fun isCardFacesSplittable(includeMessage: Boolean): Boolean {
        if (hand.cards.size != 2) {
            if (includeMessage) {
                Logz.warn("Can't split a hit hand")
            }
            return false
        } else if (!isFaceMatch) {
            if (includeMessage) {
                Logz.warn("Can't split cards that don't have the same face")
            }
            return false
        }
        return true
    }

    fun isBusted(): Boolean {
        return hand.isBusted()
    }

    fun stand() {
        hand.stand()
    }

    fun getFirstCard(): BlackjackCard {
        return hand.cards[0]
    }

    fun getSecondCard(): BlackjackCard {
        return hand.cards[1]
    }

    fun getHandValue(): Int {
        return hand.value
    }

    fun isInitialDeal(): Boolean {
        return hand.isInitialDeal
    }

    fun hasCards(): Boolean {
        return hand.hasCards()
    }

    fun getBlackjackCards(): List<BlackjackCard> {
        return hand.cards
    }

    fun isFinal(): Boolean {
        return hand.isFinal
    }

    fun isSoft(): Boolean {
        return hand.isSoft
    }

    fun isDone(): Boolean {
        return hand.isDone()
    }

    private val isFaceMatch: Boolean
        get() = hand.cards[0].card.face == hand.cards[1].card.face
    val isDoubleDowned: Boolean
        get() = wager.isDoubledDown

//    init {
//        c = handPlayer.c
//        isSplit = handPlayer.isSplit
//        isFinal = handPlayer.isFinal
//        wager = Wager(handPlayer.wager)
//        isSurrendered = handPlayer.isSurrendered
//        isSelectedEvenMoney = handPlayer.isSelectedEvenMoney
//        for (card in handPlayer.cards) {
//            cards.add(card)
//        }
//        message = handPlayer.message
//        action = handPlayer.action
//    }
}
