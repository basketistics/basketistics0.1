package de.berlin.hwr.basketistics;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;

import de.berlin.hwr.basketistics.Persistency.MockEventDB;
import de.berlin.hwr.basketistics.ViewModel.BasketisticsViewModel;

public class EventObserver implements Observer {

    private MockEventDB mockEventDB;

    private int eventID;
    // TODO: Change to actually use MockPlayerDB
    private Boolean isPoints;
    private BasketisticsViewModel basketisticsViewModel;

    // Constructor for point events
    public EventObserver(int eventID, MockEventDB mockEventDB, BasketisticsViewModel basketisticsViewModel) {
        this.eventID = eventID;
        this.mockEventDB = mockEventDB;
        this.isPoints = false;
        this.basketisticsViewModel = basketisticsViewModel;
    }

    // Constructor for all other events
    public EventObserver(MockEventDB mockEventDB, BasketisticsViewModel basketisticsViewModel) {
        this.mockEventDB = mockEventDB;
        this.isPoints = true;
        this.basketisticsViewModel = basketisticsViewModel;
    }

    @Override
    public void onChanged(@Nullable Object o) {
        if (!isPoints) {
            mockEventDB.add(basketisticsViewModel.getPlayerId().getValue(), eventID);
        } else {
            switch (basketisticsViewModel.getPoints().getValue()) {
                case 1:
                    mockEventDB.add(basketisticsViewModel.getPlayerId().getValue(), 1);
                    break;
                case 2:
                    mockEventDB.add(basketisticsViewModel.getPlayerId().getValue(), 2);
                    break;
                case 3:
                    mockEventDB.add(basketisticsViewModel.getPlayerId().getValue(), 3);
                    break;
                case -1:
                    mockEventDB.add(basketisticsViewModel.getPlayerId().getValue(), 4);
                    break;
                case -2:
                    mockEventDB.add(basketisticsViewModel.getPlayerId().getValue(), 5);
                    break;
                case -3:
                    mockEventDB.add(basketisticsViewModel.getPlayerId().getValue(), 6);
                    break;
                default:
                    // TODO: Exception handling!
            }
        }
    }
}
