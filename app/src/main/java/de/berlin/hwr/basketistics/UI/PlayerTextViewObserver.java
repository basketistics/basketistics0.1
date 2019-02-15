package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.widget.TextView;

import de.berlin.hwr.basketistics.Persistency.MockPlayerStatsDB;
import de.berlin.hwr.basketistics.ViewModel.BasketisticsViewModel;

public class PlayerTextViewObserver implements Observer {

    private MockPlayerStatsDB mockPlayerStatsDB;
    private BasketisticsViewModel basketisticsViewModel;
    private int eventIndex;
    int playerIndex;
    private TextView[][] playerTextViews;

    public PlayerTextViewObserver(
            int playerIndex,
            int eventIndex,
            MockPlayerStatsDB mockPlayerStatsDB,
            BasketisticsViewModel basketisticsViewModel,
            TextView[][] playerTextViews) {

        this.mockPlayerStatsDB = mockPlayerStatsDB;
        this.basketisticsViewModel = basketisticsViewModel;
        this.eventIndex = eventIndex;
        this.playerIndex = playerIndex;
        this.playerTextViews = playerTextViews;
    }

    @Override
    public void onChanged(@Nullable Object o) {
        // TODO: Mockup, has obviously to be changed.
        // TODO: Taking player index from ViewModel can only be done with exactly 5 players with consecutive indices.
        if (eventIndex == 0) {
            mockPlayerStatsDB.stats[basketisticsViewModel.getPlayerId().getValue() - 1][eventIndex] += basketisticsViewModel.getPoints().getValue();
        } else {
            mockPlayerStatsDB.stats[basketisticsViewModel.getPlayerId().getValue() - 1][eventIndex] += 1;
        }
        // update the matching TextView
        playerTextViews[playerIndex][eventIndex].setText("" + mockPlayerStatsDB.stats[playerIndex][eventIndex]);
    }
}
