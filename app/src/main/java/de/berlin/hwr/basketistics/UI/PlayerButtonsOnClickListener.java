package de.berlin.hwr.basketistics.UI;

import android.view.View;
import android.widget.PopupWindow;

import de.berlin.hwr.basketistics.Constants;
import de.berlin.hwr.basketistics.ViewModel.EventViewModel;

public class PlayerButtonsOnClickListener implements View.OnClickListener {

    private int playerIndex;
    private int eventID;
    private EventViewModel eventViewModel;
    private PopupWindow popupWindow;

    public PlayerButtonsOnClickListener(int playerIndex, int eventID, EventViewModel eventViewModel) {
        this.playerIndex = playerIndex;
        this.eventID = eventID;
        this.eventViewModel = eventViewModel;
    }

    public PlayerButtonsOnClickListener(int playerIndex, int eventID, EventViewModel eventViewModel, PopupWindow popupWindow) {
        this.playerIndex = playerIndex;
        this.eventID = eventID;
        this.eventViewModel = eventViewModel;
        this.popupWindow = popupWindow;
    }

    @Override
    public void onClick(View v) {

        switch (eventID) {
            case Constants.ONE_POINT:
                eventViewModel.getPlayerEvents(playerIndex).addOnePoint();
                eventViewModel.incPoints(1);
                break;
            case Constants.TWO_POINTS:
                eventViewModel.getPlayerEvents(playerIndex).addTwoPoints();
                eventViewModel.incPoints(2);
                break;
            case Constants.THREE_POINTS:
                eventViewModel.getPlayerEvents(playerIndex).addThreePoints();
                eventViewModel.incPoints(3);
                break;
            case Constants.ONE_POINT_ATTEMPT:
                eventViewModel.getPlayerEvents(playerIndex).addOnePointAttempt();
                break;
            case Constants.TWO_POINTS_ATTEMPT:
                eventViewModel.getPlayerEvents(playerIndex).addTwoPointsAttempt();
                break;
            case Constants.THREE_POINTS_ATTEMPT:
                eventViewModel.getPlayerEvents(playerIndex).addThreePointsAttempt();
                break;
            case Constants.REBOUND:
                eventViewModel.getPlayerEvents(playerIndex).addRebound();
                break;
            case Constants.ASSIST:
                eventViewModel.getPlayerEvents(playerIndex).addAssist();
                break;
            case Constants.STEAL:
                eventViewModel.getPlayerEvents(playerIndex).addSteal();
                break;
            case Constants.BLOCK:
                eventViewModel.getPlayerEvents(playerIndex).addBlock();
                break;
            case Constants.TURNOVER:
                eventViewModel.getPlayerEvents(playerIndex).addTurnover();
                break;
            case Constants.FOUL:
                eventViewModel.getPlayerEvents(playerIndex).addFoul();
                break;
            default:
                // TODO: Exception handling!
        }
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }
}
