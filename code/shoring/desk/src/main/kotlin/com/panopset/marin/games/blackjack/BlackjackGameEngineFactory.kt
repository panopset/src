package com.panopset.marin.games.blackjack

import com.panopset.blackjackEngine.BlackjackConfiguration
import com.panopset.blackjackEngine.BlackjackGameEngine

object BlackjackGameEngineFactory {

    fun create(config: BlackjackConfiguration): BlackjackGameEngine {
        return BlackjackGameEngine(config)
    }
}
