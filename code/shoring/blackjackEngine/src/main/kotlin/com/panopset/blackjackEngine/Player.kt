package com.panopset.blackjackEngine

import com.panopset.compat.Logz
import java.io.StringWriter

class Player(val wager: Wager) {
    constructor(player: Player) : this( Wager(player.wager)) {
        hands.clear() // TODO: This is kinda lame.
        for (handPlayer in player.hands) {
            hands.add(HandPlayer( handPlayer))
        }
    }

    val hands = ArrayList<HandPlayer>()
    var isSettled = false
    init {
        if (hands.isEmpty()) {
            hands.add(HandPlayer( wager))
        }
    }

    override fun toString(): String {
        val sw = StringWriter()
        for ((i, h) in hands.withIndex()) {
            sw.append(String.format("Player #%d: cards:%s value: %d ", i, h.getBlackjackCards(), h.getHandValue()))
        }
        return sw.toString()
    }

    @get:Synchronized
    val activeHand: HandPlayer?
        get() {
            for (h in hands) {
                if (!h.isFinal()) {
                    return h
                }
            }
            return null
        }

    @get:Synchronized
    val isFinal: Boolean
        get() = activeHand == null
}
