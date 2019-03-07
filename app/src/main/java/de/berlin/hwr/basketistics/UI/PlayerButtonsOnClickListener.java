package de.berlin.hwr.basketistics.UI;

import android.view.View;
import android.widget.PopupWindow;

import de.berlin.hwr.basketistics.ViewModel.EventViewModel;

public class PlayerButtonsOnClickListener implements View.OnClickListener {

    private int playerIndex;
    private int eventID;
    private int points;
    private EventViewModel eventViewModel;
    private PopupWindow popupWindow;

    public PlayerButtonsOnClickListener(int playerIndex, int eventID, EventViewModel eventViewModel) {
        this.playerIndex = playerIndex;
        this.eventID = eventID;
        this.eventViewModel = eventViewModel;
    }

    public PlayerButtonsOnClickListener(int playerIndex, int eventID, int points, EventViewModel eventViewModel, PopupWindow popupWindow) {
        this.playerIndex = playerIndex;
        this.eventID = eventID;
        this.eventViewModel = eventViewModel;
        this.points = points;
        this.popupWindow = popupWindow;
    }

    @Override
    public void onClick(View v) {

        switch (eventID) {
            case 0:
                // TODO: Exception handling, points can be null!
                eventViewModel.getPlayerEvents(playerIndex).addPoints(playerIndex, points);
                break;
            case 1:
                eventViewModel.getPlayerEvents(playerIndex).addRebound(playerIndex, 1);
                break;
            case 2:
                eventViewModel.getPlayerEvents(playerIndex).addAssist(playerIndex, 1);
                break;
            case 3:
                eventViewModel.getPlayerEvents(playerIndex).addSteal(playerIndex, 1);
                break;
            case 4:
                eventViewModel.getPlayerEvents(playerIndex).addBlock(playerIndex, 1);
                break;
            case 5:
                eventViewModel.getPlayerEvents(playerIndex).addTurnover(playerIndex, 1);
                break;
            case 6:
                eventViewModel.getPlayerEvents(playerIndex).addFoul(playerIndex, 1);
                break;
            default:
                // TODO: Exception handling!
        }
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }
}
