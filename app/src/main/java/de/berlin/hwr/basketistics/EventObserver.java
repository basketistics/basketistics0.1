package de.berlin.hwr.basketistics;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import de.berlin.hwr.basketistics.Persistency.MockEventDB;
import de.berlin.hwr.basketistics.ViewModel.BasketisticsViewModel;

public class EventObserver implements Observer {

    MockEventDB mockEventDB;

    int eventID;
    // TODO: Change to actually use MockPlayerDB
    int playerID;
    Boolean isPoints;
    BasketisticsViewModel basketisticsViewModel;

    public EventObserver(int eventID, int playerID, MockEventDB mockEventDB) {
        this.eventID = eventID;
        this.playerID = playerID;
        this.mockEventDB = mockEventDB;
        this.isPoints = false;
    }

    public EventObserver(int playerID, MockEventDB mockEventDB, BasketisticsViewModel basketisticsViewModel) {
        this.playerID = playerID;
        this.mockEventDB = mockEventDB;
        this.isPoints = true;
        this.basketisticsViewModel = basketisticsViewModel;
    }

    @Override
    public void onChanged(@Nullable Object o) {
        if (!isPoints) {
            mockEventDB.add(playerID, eventID);
        } else {
            switch (basketisticsViewModel.getPoints().getValue()) {
                case 1:
                    mockEventDB.add(playerID, 1);
                    break;
                case 2:
                    mockEventDB.add(playerID, 2);
                    break;
                case 3:
                    mockEventDB.add(playerID, 3);
                    break;
                case -1:
                    mockEventDB.add(playerID, 4);
                    break;
                case -2:
                    mockEventDB.add(playerID, 5);
                    break;
                case -3:
                    mockEventDB.add(playerID, 6);
                    break;
                default:
                    // TODO: Exception handling!
            }
        }
    }
}
