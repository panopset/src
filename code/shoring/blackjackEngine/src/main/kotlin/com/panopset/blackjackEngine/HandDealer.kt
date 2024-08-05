package com.panopset.blackjackEngine

class HandDealer() {

    private val hand = Hand()

    constructor(dealer: HandDealer) : this() {
        val iterator = dealer.hand.getIterator()
        val cardsToAddToHand = ArrayList<BlackjackCard>()
        while (iterator.hasNext()) {
            val card = iterator.next()
            cardsToAddToHand.add(card)
        }
        for (card in cardsToAddToHand) {
            hand.cards.add(card)
        }
    }

    fun getCards(): MutableList<BlackjackCard>  {
        return hand.cards
    }

    fun isFinal(): Boolean {
        return hand.isFinal
    }

    fun hasCards(): Boolean {
        return hand.hasCards()
    }

    fun dealCard(blackjackCard: BlackjackCard) {
        hand.dealCard(blackjackCard)
    }

    fun getHandValue(): Int {
        return hand.value
    }

    fun getFirstCard(): BlackjackCard {
        return hand.cards[0]
    }

    fun getSecondCard(): BlackjackCard {
        return hand.cards[1]
    }

    fun isSoft(): Boolean {
        return hand.isSoft
    }

    fun stand() {
        hand.stand()
    }

    fun isNatural21(): Boolean {
        return hand.isNatural21()
    }

    val upCard: BlackjackCard
        get() = hand.cards[1]

    val downCard: BlackjackCard
        get() = hand.cards[0]
}
