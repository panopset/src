package com.panopset.marin.oldswpkg.games.blackjack;

import com.panopset.compat.Tile;
import com.panopset.blackjackEngine.CycleSnapshot;

public class LayoutDealer extends Layout {

    public LayoutDealer(Tile t, int[] cardDim, CycleSnapshot cs) {
        super(t, cardDim, cs);
    }


    private Integer nextDealerX;

    public Integer getNextDealerX() {
        if (nextDealerX == null) {
            nextDealerX = t.getLeft();
        } else {
            nextDealerX = nextDealerX + cardSpacer;
        }
        return nextDealerX;
    }

}
