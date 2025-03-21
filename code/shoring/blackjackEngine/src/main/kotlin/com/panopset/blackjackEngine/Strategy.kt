package com.panopset.blackjackEngine

import java.util.*

class Strategy(blackjackConfiguration: BlackjackConfiguration) : Configurable(blackjackConfiguration) {

    var rawData: List<String> = blackjackConfiguration.getStrategyData()
    private var sls: Map<StratCat, MutableMap<String, StrategyLine>>? = createStrategyLineMap()
    private lateinit var hardHeader: String
    private lateinit var softHeader: String
    private lateinit var splitHeader: String

    init {
        if (rawData.isEmpty()) {
            throw RuntimeException("no data for strategy found.")
        }
        populateStrategyLineMap()
    }


    fun getRecommendation(s: Situation): String {
        val line = findStrategyLine(s)
        return line!!.getAction(s)
    }

    fun getHeaderFor(cat: StratCat?): String {
        return when (cat) {
            StratCat.SOFT -> softHeader
            StratCat.SPLIT -> splitHeader
            else -> hardHeader
        }
    }


    private fun populateStrategyLineMap() {
        var reading = StratCat.NONE
        for (s in rawData) {
            if (s.isEmpty() || s.substring(0, 1) == "#") {
                continue
            }
            if (StratCat.HARD.toString().equals(s.substring(0, 4), ignoreCase = true)) {
                reading = StratCat.HARD
                hardHeader = s
            } else if (StratCat.SOFT.toString().equals(s.substring(0, 4), ignoreCase = true)) {
                reading = StratCat.SOFT
                softHeader = s
            } else if (StratCat.SPLIT.toString().equals(s.substring(0, 5), ignoreCase = true)) {
                reading = StratCat.SPLIT
                splitHeader = s
            } else {
                if (StratCat.HARD == reading) {
                    val sl = StrategyLine(StratCat.HARD, s, blackjackConfiguration)
                    sls!![StratCat.HARD]!![sl.key] = sl
                } else if (StratCat.SOFT == reading) {
                    val sl = StrategyLine(StratCat.SOFT, s, blackjackConfiguration)
                    sls!![StratCat.SOFT]!![sl.key] = sl
                } else {
                    val sl = StrategyLine(StratCat.SPLIT, s, blackjackConfiguration)
                    sls!![StratCat.SPLIT]!![sl.key] = sl
                }
            }
        }
    }

    fun findStrategyLine(s: Situation): StrategyLine? {
        if (s.handPlayer == null) {
            return null
        }
        var rtn: StrategyLine? = null
        if (shouldSplit(s)) {
            rtn = findSplitStrategyLine(s)
        }
        if (rtn != null) {
            return rtn
        }
        if (s.handPlayer.isSoft()) {
            rtn = findSoftStrategyLine(s)
        }
        return rtn ?: findHardStrategyLine(s)
    }

    private fun shouldSplit(s: Situation): Boolean {
        if (s.handPlayer == null) {
            return false
        }
        if (s.handPlayer.isInitialDeal() && s.handPlayer.isCardFacesSplittable) {
            val v = s.handPlayer.getFirstCard().getHardValue()
            return v != 5 && v != 10
        }
        return false
    }

    private fun findSplitStrategyLine(s: Situation): StrategyLine? {
        if (s.handPlayer == null) {
            return null
        }
        val cv = s.handPlayer.getFirstCard().getHardValue()
        var key = "$cv,$cv"
        if (key == "1,1") {
            key = "A,A"
        }
        return sls!![StratCat.SPLIT]!![key]
    }

    private fun findSoftStrategyLine(s: Situation): StrategyLine? {
        if (s.handPlayer == null) {
            return null
        }
        val v = s.handPlayer.getHandValue()
        val key = if (v > 19) "20+" else v.toString()
        return sls!![StratCat.SOFT]!![key]
    }

    private fun findHardStrategyLine(s: Situation): StrategyLine? {
        if (s.handPlayer == null) {
            return null
        }
        val v = s.handPlayer.getHandValue()
        val key = if (v > 17) {
            "18+"
        } else if (v < 8) {
            "7"
        } else {
            v.toString()
        }
        return sls!![StratCat.HARD]!![key]
    }

    private fun createStrategyLineMap(): Map<StratCat, MutableMap<String, StrategyLine>> {
        val rtn: MutableMap<StratCat, MutableMap<String, StrategyLine>> = EnumMap(
            StratCat::class.java
        )
        rtn[StratCat.HARD] = HashMap()
        rtn[StratCat.SOFT] = HashMap()
        rtn[StratCat.SPLIT] = HashMap()
        return rtn
    }
}
