package de.berlin.hwr.basketistics;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import de.berlin.hwr.basketistics.Persistency.MockEventDB;
import de.berlin.hwr.basketistics.Persistency.MockEventTypeDB;
import de.berlin.hwr.basketistics.Persistency.MockPlayerDB;

public class EventObserver implements Observer {

    // TODO: DBs in wrong place!!
    MockEventDB mockEventDB = new MockEventDB();
    MockEventTypeDB mockEventTypeDB = new MockEventTypeDB();
    MockPlayerDB mockPlayerDB = new MockPlayerDB();

    int eventID;
    // TODO: Change to actually use MockPlayerDB
    int playerID;

    public EventObserver(int eventID, int playerID) {
        this.eventID = eventID;
        this.playerID = playerID;
    }

    @Override
    public void onChanged(@Nullable Object o) {
        mockEventDB.add(playerID, eventID);
    }
}
