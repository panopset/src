package com.panopset.blackjackEngine

import java.io.StringWriter

class Hand {
    var value = 0
        private set

    var isSoft = false

    val cards: MutableList<BlackjackCard> = ArrayList()

    fun getIterator(): Iterator<BlackjackCard> {
        val rtn = ArrayList<BlackjackCard>()

        //TODO: ConcurrentModificationException
        for (card in cards) {
            rtn.add(card)
        }
        return rtn.iterator()
    }

    fun isNatural21(): Boolean {
        return isInitialDeal && value == MAX
    }

    fun getHardValueOf(i: Int): Int {
        return cards[i].getHardValue()
    }

    fun hasCards(): Boolean {
        return cards.isNotEmpty()
    }

    val isInitialDeal: Boolean
        get() = hasCards() && cards.size == 2

    fun remove(i: Int): BlackjackCard {
        return cards.removeAt(i)
    }

    fun dealCard(card: BlackjackCard) {
        cards.add(card)
        if (cards.size > 1) {
            setValue()
        }
    }

    private fun setValue() {
        value = 0
        var margin = 0
        isSoft = false
        for (card in cards) {
            value += card.getHardValue()
            if (card.isAce()) {
                margin += 10
            }
        }
        while (value < 12 && margin > 0) {
            value += 10
            margin -= 10
            isSoft = true
        }
    }

    fun isDone(): Boolean {
        return isBusted() || value == MAX
    }

    fun isBusted(): Boolean {
        val rtn = value > MAX
        if (rtn) {
            stand()
        }
        return rtn
    }

    fun stand() {
        isFinal = true
    }

    var isFinal = false

    override fun toString(): String {
        val sw = StringWriter()
        sw.append(String.format("*HAND>>* %d: ", value))
        var firstTime = true
        for (card in cards) {
            if (firstTime) {
                firstTime = false
            } else {
                sw.append(" ")
            }
            sw.append(card.toString())
        }
        return sw.toString()
    }

    companion object {
        const val MAX = 21
    }
}
