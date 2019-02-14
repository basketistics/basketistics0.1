package de.berlin.hwr.basketistics;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import de.berlin.hwr.basketistics.Persistency.MockEventDB;

public class EventObserver implements Observer {

    MockEventDB mockEventDB;

    int eventID;
    // TODO: Change to actually use MockPlayerDB
    int playerID;

    public EventObserver(int eventID, int playerID, MockEventDB mockEventDB) {
        this.eventID = eventID;
        this.playerID = playerID;
        this.mockEventDB = mockEventDB;
    }

    @Override
    public void onChanged(@Nullable Object o) {
        mockEventDB.add(playerID, eventID);
    }
}
