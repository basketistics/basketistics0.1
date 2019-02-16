package de.berlin.hwr.basketistics.UI;

import android.view.View;
import android.widget.PopupWindow;

import de.berlin.hwr.basketistics.ViewModel.BasketisticsViewModel;


public class PlayerButtonsOnClickListener implements View.OnClickListener {

    private int player;
    private int eventID;
    private int points;
    private BasketisticsViewModel basketisticsViewModel;
    PopupWindow window;

    public PlayerButtonsOnClickListener(int player, int eventID, BasketisticsViewModel basketisticsViewModel, PopupWindow window) {
        this.window = window;
        this.player = player;
        this.eventID = eventID;
        this.basketisticsViewModel = basketisticsViewModel;
    }

    public PlayerButtonsOnClickListener(int player, int eventID, BasketisticsViewModel basketisticsViewModel) {
        this.player = player;
        this.eventID = eventID;
        this.basketisticsViewModel = basketisticsViewModel;
    }

    public PlayerButtonsOnClickListener(int player, int eventID, int points, BasketisticsViewModel basketisticsViewModel, PopupWindow window) {
        this.window = window;
        this.player = player;
        this.eventID = eventID;
        this.basketisticsViewModel = basketisticsViewModel;
        this.points = points;
    }

    @Override
    public void onClick(View v) {

        // set PlayerID in ViewModel to make it interpretable for the database-stuff
        // player starts with 0 so that's normalized here
        basketisticsViewModel.setPlayerId(player + 1);

        switch (eventID) {
            case 0:
                // TODO: Exception handling, points can be null!
                basketisticsViewModel.setPoints(points);
                break;
            case 1:
                basketisticsViewModel.setRebound(1);
                break;
            case 2:
                basketisticsViewModel.setAssist(1);
                break;
            case 3:
                basketisticsViewModel.setSteal(1);
                break;
            case 4:
                basketisticsViewModel.setBlock(1);
                break;
            case 5:
                basketisticsViewModel.setTurnover(1);
                break;
            case 6:
                basketisticsViewModel.setFoul(1);
                break;
            default:
                // TODO: Exception handling
        }
        if (window != null)
            window.dismiss();
    }
}
