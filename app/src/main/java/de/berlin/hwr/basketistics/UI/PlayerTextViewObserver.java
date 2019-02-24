package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import de.berlin.hwr.basketistics.Persistency.MockPlayerStatsDB;
import de.berlin.hwr.basketistics.ViewModel.BasketisticsViewModel;
import de.berlin.hwr.basketistics.ViewModel.EventViewModel;

public class PlayerTextViewObserver implements Observer {

    private static final String TAG = "PlayerTextVeiwObserver";

    private EventViewModel eventViewModel;
    private int eventIndex;
    int playerIndex;
    private TextView[][] playerTextViews;

    public PlayerTextViewObserver(
            int playerIndex,
            int eventIndex,
            EventViewModel eventViewModel,
            TextView[][] playerTextViews) {

        this.eventViewModel = eventViewModel;
        this.eventIndex = eventIndex;
        this.playerIndex = playerIndex;
        this.playerTextViews = playerTextViews;
    }

    @Override
    public void onChanged(@Nullable Object o) {

        switch (eventIndex) {
            case 0:
                playerTextViews[playerIndex][eventIndex]
                        .setText("" + eventViewModel.getPlayerEvents(playerIndex).getPoints());
                break;
            case 1:
                playerTextViews[playerIndex][eventIndex]
                        .setText("" + eventViewModel.getPlayerEvents(playerIndex).getRebound());
                break;
            case 2:
                playerTextViews[playerIndex][eventIndex]
                        .setText("" + eventViewModel.getPlayerEvents(playerIndex).getAssist());
                break;
            case 3:
                playerTextViews[playerIndex][eventIndex]
                        .setText("" + eventViewModel.getPlayerEvents(playerIndex).getSteal());
                break;
            case 4:
                playerTextViews[playerIndex][eventIndex]
                        .setText("" + eventViewModel.getPlayerEvents(playerIndex).getBlock());
                break;
            case 5:
                playerTextViews[playerIndex][eventIndex]
                        .setText("" + eventViewModel.getPlayerEvents(playerIndex).getTurnover());
                break;
            case 6:
                playerTextViews[playerIndex][eventIndex]
                        .setText("" + eventViewModel.getPlayerEvents(playerIndex).getFoul());
                break;
        }

    }
}
