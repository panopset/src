package com.panopset.marin.oldswpkg.games.blackjack;

import static com.panopset.blackjackEngine.CommandDefinitions.CMD_AUTO;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_COUNT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DEAL;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DECREASE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_DOUBLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_HIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_INCREASE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_RESET;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SHUFFLE;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SPLIT;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_STAND;
import static com.panopset.blackjackEngine.CommandDefinitions.CMD_SURRENDER;

import com.panopset.marin.compat.util.CommandBinder;

public class BlackjackCmdBinder extends CommandBinder {
  public BlackjackCmdBinder() {
    super();
    registerCommand(new CommandBinder.Command("Deal", CMD_DEAL, 0));
    registerCommand(new CommandBinder.Command("Hit", CMD_HIT, 0));
    registerCommand(new CommandBinder.Command("Surrender", CMD_SURRENDER, 0));
    registerCommand(new CommandBinder.Command("Stand", CMD_STAND, 0));
    registerCommand(new CommandBinder.Command("Split", CMD_SPLIT, 0));
    registerCommand(new CommandBinder.Command("Double", CMD_DOUBLE, 0));
    registerCommand(new CommandBinder.Command("Increase", CMD_INCREASE, 0));
    registerCommand(new CommandBinder.Command("Decrease", CMD_DECREASE, 0));
    registerCommand(new CommandBinder.Command("Reset", CMD_RESET, 0));
    registerCommand(new CommandBinder.Command("Shuffle", CMD_SHUFFLE, 0));
    registerCommand(new CommandBinder.Command("Auto", CMD_AUTO, 0));
    registerCommand(new CommandBinder.Command("Count", CMD_COUNT, 0));
  }
}
