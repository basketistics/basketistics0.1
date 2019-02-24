package de.berlin.hwr.basketistics.UI;

import android.view.View;

import de.berlin.hwr.basketistics.Persistency.Entities.EventEntity;
import de.berlin.hwr.basketistics.ViewModel.BasketisticsViewModel;
import de.berlin.hwr.basketistics.ViewModel.EventViewModel;

public class PlayerButtonsOnClickListener implements View.OnClickListener {

    private int playerIndex;
    private int eventID;
    private int points;
    private EventViewModel eventViewModel;

    public PlayerButtonsOnClickListener(int playerIndex, int eventID, EventViewModel eventViewModel) {
        this.playerIndex = playerIndex;
        this.eventID = eventID;
        this.eventViewModel = eventViewModel;
    }

    public PlayerButtonsOnClickListener(int playerIndex, int eventID, int points, EventViewModel eventViewModel) {
        this.playerIndex = playerIndex;
        this.eventID = eventID;
        this.eventViewModel = eventViewModel;
        this.points = points;
    }

    @Override
    public void onClick(View v) {

        switch (eventID) {
            case 0:
                // TODO: Exception handling, points can be null!
                eventViewModel.getPlayerEvents(playerIndex).addPoints(points);
                break;
            case 1:
                eventViewModel.getPlayerEvents(playerIndex).addRebound(1);
                break;
            case 2:
                eventViewModel.getPlayerEvents(playerIndex).addAssist(1);
                break;
            case 3:
                eventViewModel.getPlayerEvents(playerIndex).addSteal(1);
                break;
            case 4:
                eventViewModel.getPlayerEvents(playerIndex).addBlock(1);
                break;
            case 5:
                eventViewModel.getPlayerEvents(playerIndex).addTurnover(1);
                break;
            case 6:
                eventViewModel.getPlayerEvents(playerIndex).addFoul(1);
                break;
            default:
                // TODO: Exception handling!
        }
    }
}
