package com.panopset.blackjackEngine

data class BlackjackGameState(
    val chips: Long,
    val reloadCount: Long,
    val reloadAmount: Int,
    val action: String,
    val handDealer: HandDealer,
    val players: List<Player>,
    val metrics: Metrics,
    val nextBet: Int,
    val mistakeHeader: String,
    val mistakeMessage: String,
    val dealerMessage: String,
    val gameStatusVertical: List<String>,
    val gameStatusHorizontal: String,
    val statusChipsVertical: List<String>,
    val statusChipsHorizontal: String,
    val stakeIncludingHands: Long
) {
    constructor(bge: BlackjackGameEngine) : this(
        bge.bankroll.getChips(),
        bge.bankroll.reloadCount,
        bge.config.getReloadAmountInWholeDollars() * 100,
        bge.action,
        bge.ct.cloneDealer(),
        bge.ct.clonePlayers(),
        Metrics(bge.metrics),
        bge.getNextBet(),
        bge.mistakeHeader,
        bge.mistakeMessage,
        bge.dealerMessage,
        bge.getGameStatusVertical(),
        bge.getGameStatusHorizontal(),
        bge.getStatusChipsVertical(),
        bge.getStatusChipsHorizontal(),
        bge.bankroll.getStakeIncludingHands(bge.getCycle().players)
    )
    fun isEmpty(): Boolean {
        return players.isEmpty()
    }
}
