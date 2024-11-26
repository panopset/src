package com.panopset.blackjackEngine

import com.panopset.compat.Streamop.getTextFromStream

class DefaultResources() {
    val defaultBasicStrategy = getTextFromStream(this.javaClass.getResourceAsStream(DEFAULT_STRATEGY_RSRC))
    val defaultCountingSystems = getTextFromStream(this.javaClass.getResourceAsStream(DEFAULT_COUNTING_SYSTEMS_RSRC))
}

private const val DEFAULT_STRATEGY_RSRC = "/basic.txt"
private const val DEFAULT_COUNTING_SYSTEMS_RSRC = "/cs.txt"
