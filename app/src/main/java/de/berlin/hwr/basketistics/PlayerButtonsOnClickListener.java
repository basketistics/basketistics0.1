package de.berlin.hwr.basketistics;

import android.view.View;

import de.berlin.hwr.basketistics.ViewModel.BasketisticsViewModel;

public class PlayerButtonsOnClickListener implements View.OnClickListener {

    private int player;
    private int eventID;
    private int points;
    private BasketisticsViewModel basketisticsViewModel;

    public PlayerButtonsOnClickListener(int player, int eventID, BasketisticsViewModel basketisticsViewModel) {
        this.player = player;
        this.eventID = eventID;
        this.basketisticsViewModel = basketisticsViewModel;
    }

    public PlayerButtonsOnClickListener(int player, int eventID, int points, BasketisticsViewModel basketisticsViewModel) {
        this.player = player;
        this.eventID = eventID;
        this.basketisticsViewModel = basketisticsViewModel;
    }

    @Override
    public void onClick(View v) {

        // set PlayerID in ViewModel to make it interpretable for the database-stuff
        basketisticsViewModel.setPlayerId(player + 1);

        switch (eventID) {
            case 0:
                // TODO: Exception handling, points can be null!
                basketisticsViewModel.setPoints(points);
                break;
            case 1:
                basketisticsViewModel.setAssist(1);
                break;
            case 2:
                basketisticsViewModel.setRebound(1);
                break;
            case 3:
                basketisticsViewModel.setFoul(1);
                break;
            case 4:
                basketisticsViewModel.setBlock(1);
                break;
            case 5:
                basketisticsViewModel.setTurnover(1);
                break;
            case 6:
                basketisticsViewModel.setSteal(1);
                break;
            default:
                // TODO: Exception handling!
        }
    }
}
