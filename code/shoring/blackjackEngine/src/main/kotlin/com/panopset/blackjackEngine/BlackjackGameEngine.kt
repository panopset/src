package com.panopset.blackjackEngine

import com.panopset.compat.*
import java.io.StringWriter
import java.util.*

open class BlackjackGameEngine(private val logDisplayer: LogDisplayer, val config: BlackjackConfiguration) {
    constructor(config: BlackjackConfiguration) : this(LogzDisplayerCMD, config)

    var action = ""
    private val blackjackShoe = BlackjackShoe()

    /**
     * Only needed for JUnit tests.
     */
    fun getShoe(): BlackjackShoe {
        return blackjackShoe
    }

    var metrics = Metrics()
    val ct = CycleController(this)
    private val msg: BlackjackMessages = config.getMessages()


    var strategy = Strategy(config)
    var countingSystems = config.getCountingSystems()


    val bankroll = Bankroll()

    open fun exec(action: String) {
        Logz.logzDsiplayer = logDisplayer
        frontEndPreInitCheck()
        performAction(action)
        takeAnewSnapshot()
    }

    fun frontEndPreInitCheck() {
        if (bankroll.reloadAmount == 0) {
            reset()
        }
    }

    fun deal(isShowing: Boolean): BlackjackCard {
        return blackjackShoe.deal(isShowing, countingSystems)
    }

    private fun reset() {
        countingSystems = config.getCountingSystems()
        bankroll.reloadAmount = config.getReloadAmountInWholeDollars() * 100
        if (bankroll.reloadAmount < 0) {
            throw RuntimeException()
        }
        metrics.reset()
        ct.reset()
        bankroll.reset()
        countingSystems.resetCount()
        blackjackShoe.numberOfDecks = config.getDecks()
        resetNextBet()
        blackjackShoe.shuffle()
        Logz.clear()
    }

    private fun performAction(action: String) {
        this.action = action
        if (performAdminAction(action)) {
            return
        }
        val ra = getCycle().getRecommendedAction()
        if (!isPossibleAction(action, ra)) {
            return
        }
        var recommendedActionHeader = ""
        var recommendedActionStrategyLine = ""
        if (action != ra) {
            val sl = getCycle().getStrategyLine()
            if (sl != null) {
                recommendedActionStrategyLine = sl.source
                recommendedActionHeader = strategy.getHeaderFor(sl.stratCat)
            }
        }
        if (CMD_DEAL == action) {
            dealNextHand()
        }
        var player = checkForFinish()
        if (player != null) {
            val handPlayer = player.activeHand
            if (performAction(player, handPlayer, action)) {
                if (handPlayer != null) {
                    handPlayer.action = action
                }
                player = checkForFinish()
                updateMessages(recommendedActionHeader, recommendedActionStrategyLine)
            }
        }
        reasonThisGameExists(player)
    }

    private fun dealNextHand() {
        if (isShuffleNeeded) {
            shuffle()
            countingSystems.resetCount()
        }
        if (getCycle().isDealt) {
            ct.reset()
        }
        getCycle().deal()
    }

    /**
     * If you are practicing the basic strategy, you don't care about the outcome of the hand, just
     * stop the game and flag the player if there is a mistake, otherwise just keep going on to the
     * next hand, no need to click deal.
     *
     * @param player Player.
     */
    private fun reasonThisGameExists(player: Player?) {
        if (player == null && config.isFastDeal()
            && !isAutomaticRunning() && "" == mistakeMessage
        ) {
            exec(CMD_DEAL)
        }
    }

    private fun checkForFinish(): Player? {
        val player = getCycle().getActivePlayer()
        if (player == null) {
            getCycle().finish()
        }
        return player
    }

    private fun performAdminAction(action: String): Boolean {
        clearMessages()
        return when (action) {
            CMD_RESET -> {
                reset()
                dealerMessage = msg.resetMsg
                true
            }

            CMD_COUNT -> {
                config.toggleShowCount()
                true
            }

            CMD_SHUFFLE -> {
                if (ct.isActive) {
                    dealerMessage = String.format("%s.", msg.handActiveMsg)
                    return true
                }
                shuffle()
                true
            }

            CMD_INCREASE -> {
                increase()
                true
            }

            CMD_DECREASE -> {
                decrease()
                true
            }

            CMD_AUTO -> {
                auto()
                true
            }

            else -> false
        }
    }

    private fun hit(handPlayer: HandPlayer) {
        handPlayer.dealCard(blackjackShoe.deal(true, countingSystems))
        if (handPlayer.isBusted()) {
            handPlayer.message = msg.bustedMsg
            handPlayer.wager.lost()
        }
    }

    private fun stand(hand: HandPlayer) {
        hand.stand()
    }

    private fun surrender(handPlayer: HandPlayer?): Boolean {
        if (!config.isLateSurrenderAllowed()) {
            dealerMessage = msg.surrenderNotAllowedMsg
            return false
        }
        if (handPlayer != null) {
            if (!handPlayer.isInitialDeal()) {
                dealerMessage = msg.surrenderImpossibleMsg
                return false
            }
        }
        handPlayer?.surrender()
        return true
    }

    private fun split(player: Player, handPlayer: HandPlayer): Boolean {
        if (!handPlayer.isCardFacesSplittableIncludeMessage) {
            dealerMessage = Logz.getPriorMessage()
            return false
        }
        bankroll.subtract(handPlayer.wager.initialBet)
        val splitHand = HandPlayer(Wager(handPlayer.wager))
        val splitCard = handPlayer.removeSecondCard()
        handPlayer.dealCard(deal(true))
        handPlayer.setSplit()
        if (handPlayer.getFirstCard().isAce() && !(config.isResplitAcesAllowed() && handPlayer.getSecondCard()
                .isAce())
        ) {
            if (!config.isSplitAcePlayable()) {
                handPlayer.stand()
            }
        }
        splitHand.dealCard(splitCard)
        splitHand.dealCard(deal(true))
        splitHand.setSplit()
        splitHand.action = CMD_SPLIT
        player.hands.add(splitHand)
        if (splitCard.isAce()) {
            if (!(config.isResplitAcesAllowed() && splitHand.getSecondCard().isAce())) {
                if (!config.isSplitAcePlayable()) {
                    splitHand.stand()
                }
            }
        }
        if (handPlayer.getHandValue() == 21) {
            handPlayer.stand()
        }
        if (splitHand.getHandValue() == 21) {
            splitHand.stand()
        }
        return true
    }

    private fun dbl(handPlayer: HandPlayer): Boolean {
        if (!handPlayer.canDouble(config.isDoubleAfterSplitAllowed())) {
            dealerMessage = msg.doubleImpossibleMsg
            return false
        }
        handPlayer.dealCard(blackjackShoe.deal(true, countingSystems))
        handPlayer.stand()
        bankroll.subtract(handPlayer.wager.initialBet)
        doubleDownWager(handPlayer)
        return true
    }

    private fun doubleDownWager(handPlayer: HandPlayer) {
        handPlayer.wager.doubleDown()
    }

    private fun performAction(player: Player, handPlayer: HandPlayer?, action: String): Boolean {
        if (handPlayer == null) {
            return false
        }
        return when (action) {
            CMD_SURRENDER -> surrender(handPlayer)
            CMD_HIT -> {
                hit(handPlayer)
                true
            }

            CMD_DOUBLE -> dbl(handPlayer)
            CMD_SPLIT -> split(player, handPlayer)
            CMD_STAND -> {
                stand(handPlayer)
                true
            }

            else -> false
        }
    }

    private fun isPossibleAction(action: String, ra: String): Boolean {
        if (CMD_DEAL == action) {
            if (CMD_DEAL != ra) {
                dealerMessage = String.format("%s.", msg.handActiveMsg)
                return false
            }
        } else {
            if (CMD_DEAL == ra) {
                dealerMessage = String.format("%s L=%s", msg.pleaseSelectMsg, msg.dealMsg)
                return false
            }
        }
        return true
    }

    private fun updateMessages(
        recommendedActionHeader: String,
        recommendedActionStrategyLine: String
    ) {
        if (Stringop.isPopulated(recommendedActionStrategyLine)) {
            mistakeHeader = recommendedActionHeader
            mistakeMessage = recommendedActionStrategyLine
            metrics.incrementMistakeCount()
        }
    }

    private val isShuffleNeeded: Boolean
        get() = blackjackShoe.remaining() < blackjackShoe.cut()

    fun setNextBet(value: Int) {
        if (value == 0) {
            return
        }
        nextBet = value
    }

    private val wagerIncrement: Int
        get() = config.getBetIncrementInWholeDollars() * 100

    private fun increase() {
        setNextBet(getNextBet() + wagerIncrement)
        updateInactiveHands()
    }

    private fun decrease() {
        setNextBet(getNextBet() - wagerIncrement)
        updateInactiveHands()
    }

    private fun updateInactiveHands() {
        for (player in getCycle().players) {
            for (handPlayer in player.hands) {
                if (!handPlayer.hasCards()) {
                    bankroll.subtract(-handPlayer.wager.initialBet)
                    handPlayer.wager.initialBet = getNextBet()
                    bankroll.subtract(handPlayer.wager.initialBet)
                }
            }
        }
    }

    fun getCycle(): Cycle {
        return ct.getCycle(this, strategy)
    }

    fun isAutomaticRunning(): Boolean {
        return automatic.name.equals(automaticRunningName)
    }

    private fun auto() {
        if (isAutomaticRunning()) {
            stopAutomaticThread()
        } else {
            if (isReadyToRunAutomatically()) {
                startAutomaticThread()
            }
        }
    }

    private fun stopAutomaticThread() {
        automatic.name = "PanStopRequested"
    }

    private var automatic = createNewAutomaticThread()

    private fun createNewAutomaticThread(): Thread {
        return Thread {
            doAutomatic()
        }
    }

    private val automaticRunningName = "PanAutomatic"

    private fun startAutomaticThread() {
        if (automaticRunningName == automatic.name) {
            return
        }
        automatic = createNewAutomaticThread()
        automatic.name = automaticRunningName
        automatic.start()
    }

    private fun doAutomatic() {
        while (isAutomaticRunning() && Zombie.isActive) {
            val ra: String = getCycle().getRecommendedAction()
            if (CMD_DEAL == ra) {
                val targetStake: Int = config.getTargetStakeInWholeDollars() * 100
                if (targetStake > 0 && bankroll.getStakeOutOfPlay() >= targetStake) {
                    Logz.green(
                        "Target stake of ${Stringop.getDollarString(targetStake.toLong())} " +
                                "reached, automatic execution ended."
                    )
                    stopAutomaticThread()
                    return
                }
            }
            exec(ra)
        }
    }

    private fun isReadyToRunAutomatically(): Boolean {
        if (isAutomaticRunning()) {
            return false
        }
        if (config.isBasicStrategyVariationsOnly()) {
            val message =
                "Please turn off \"Variations\" in the Configuration->Rules tab before running automatically."
            Logz.warn(message)
            dealerMessage = message
            return false
        }
        return true
    }

    fun shuffle() {
        blackjackShoe.shuffle()
        countingSystems.resetCount()
        dealerMessage = if (blackjackShoe.isTheDeckStacked()) {
            "Shuffled and stacked deck for debugging"
        } else {
            msg.shuffledMsg
        }
    }

    private var nextBet = 0
    private val minBet: Int
        get() = 100 * config.getMinimumBetInWholeDollars()

    fun getNextBet(): Int {
        if (nextBet == 0) {
            nextBet = minBet
        }
        return nextBet
    }

    private fun resetNextBet() {
        nextBet = minBet
    }

    var mistakeMessage: String = ""
    var mistakeHeader: String = ""
    var dealerMessage: String = ""

    private fun clearMessages() {
        mistakeHeader = ""
        mistakeMessage = ""
        dealerMessage = ""
    }

    fun getGameStatusVertical(): List<String> {
        val rtn: MutableList<String> = ArrayList()
        val st = StringTokenizer(getGameStatus(), "|")
        while (st.hasMoreElements()) {
            rtn.add(st.nextToken().trim { it <= ' ' })
        }
        return rtn
    }

    fun getGameStatusHorizontal(): String {
        return getGameStatus().replace("|", "")
    }

    fun getStatusChipsVertical(): List<String> {
        val rtn: MutableList<String> = ArrayList()
        val st = StringTokenizer(getRawStatusChips(), "|")
        while (st.hasMoreElements()) {
            rtn.add(st.nextToken().trim { it <= ' ' })
        }
        return rtn
    }

    fun getStatusChipsHorizontal(): String {
        return getRawStatusChips().replace("|", "")
    }

    fun getGameStatus(): String {
        val sw = StringWriter()
        sw.append("| Stake: ${Stringop.getDollarString(bankroll.getStakeIncludingHands(getCycle().players))}")
        sw.append("| Reloads: " + bankroll.reloadCount)
        sw.append("| Chips: ${Stringop.getDollarString(bankroll.getChips())}")
        sw.append("| Next bet: ${Stringop.getDollarString(getNextBet().toLong())}")
        sw.append("| Score: " + metrics.handsSinceLastMistake)
        sw.append(" (" + metrics.getHandsSinceLastMistakeRecord() + ")")
        if (config.isShowCount()) {
            sw.append("| ")
            sw.append(countingSystems.findSelected().name)
            sw.append(": " + countingSystems.findSelected().count)
        }
        return sw.toString()
    }

    fun getRawStatusChips(): String {
        val sw = StringWriter()
        sw.append("  reloads: " + bankroll.reloadCount)
        sw.append("|  Chips: " + Stringop.getDollarString(bankroll.getChips()))
        sw.append("| Next bet: ${Stringop.getDollarString(getNextBet().toLong())}")
        return sw.toString()
    }

    fun reportNewHand() {
        if (isAutomaticRunning()) {
            metrics.reportNewHandAutomatic()
        } else {
            metrics.reportNewHand()
        }
    }

    var isShuffleFlagOn = false
        private set

    fun triggerShuffleBeforeNextHand() {
        isShuffleFlagOn = true
    }

    var isBustedPriorHand = false
        private set

    fun setPriorHandBustedFlag() {
        isBustedPriorHand = true
    }

    fun clearPriorHandBustedFlag() {
        isBustedPriorHand = false
    }

    var streak = 0
        private set

    fun incrementStreak() {
        streak++
    }

    fun resetStreak() {
        streak = 0
    }

    fun getTotalValue(): Long {
        return bankroll.getStakeIncludingHands(ct.cycle?.players)
    }

    /**
     * Testing only.
     */
    fun waitMillis(i: Long) {
        Thread.sleep(i)
    }

    fun isCountVeryPositive(): Boolean {
        if (config.getStrategicVeryPositiveCount() == 0) {
            return false
        }
        val trueCount = countingSystems.getTrueCount()
        val veryPositiveCount = config.getStrategicVeryPositiveCount()
        return trueCount > veryPositiveCount
    }

    private lateinit var lastActionSnapshot: BlackjackGameState
    private var firstTime = true

    fun getLatestSnapshot(): BlackjackGameState {
        if (firstTime) {
            takeAnewSnapshot()
            firstTime = false
        }
        return lastActionSnapshot
    }

    private fun takeAnewSnapshot() {
        lastActionSnapshot = BlackjackGameState(this)
    }

    fun toggleShowCount() {
        config.toggleShowCount()
    }
}
