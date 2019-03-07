package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.widget.TextView;

import de.berlin.hwr.basketistics.ViewModel.EventViewModel;

public class PlayerViewObserver implements Observer {

    private static final String TAG = "PlayerTextVeiwObserver";

    private EventViewModel eventViewModel;
    private int eventIndex;
    int playerIndex;
    private TextView[][] playerTextViews;

    public PlayerViewObserver(
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
                        .setText("" + eventViewModel.getPlayerEvents(playerIndex).getPoints().getValue());
                break;
            case 1:
                playerTextViews[playerIndex][eventIndex]
                        .setText("" + eventViewModel.getPlayerEvents(playerIndex).getRebound().getValue());
                break;
            case 2:
                playerTextViews[playerIndex][eventIndex]
                        .setText("" + eventViewModel.getPlayerEvents(playerIndex).getAssist().getValue());
                break;
            case 3:
                playerTextViews[playerIndex][eventIndex]
                        .setText("" + eventViewModel.getPlayerEvents(playerIndex).getSteal().getValue());
                break;
            case 4:
                playerTextViews[playerIndex][eventIndex]
                        .setText("" + eventViewModel.getPlayerEvents(playerIndex).getBlock().getValue());
                break;
            case 5:
                playerTextViews[playerIndex][eventIndex]
                        .setText("" + eventViewModel.getPlayerEvents(playerIndex).getTurnover().getValue());
                break;
            case 6:
                playerTextViews[playerIndex][eventIndex]
                        .setText("" + eventViewModel.getPlayerEvents(playerIndex).getFoul().getValue());
                break;
        }

    }
}
