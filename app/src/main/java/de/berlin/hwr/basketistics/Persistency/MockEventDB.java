package de.berlin.hwr.basketistics.Persistency;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MockEventDB {

    class Entry {
        int index = 0;
        int playerID = 0;
        int eventID = 0;
        Timestamp timestamp;

        public Entry(int index, int playerID, int eventID) {
            this.playerID = playerID;
            this.eventID = eventID;
            this.timestamp = new Timestamp(System.nanoTime());
        }
    }

    // This.index and Entry.index should always be the same
    int index = 0;
    List<Entry> db = new ArrayList<Entry>();

    public void add(int playerID, int eventID) {
        db.add(new Entry(index, playerID, eventID));
        index++;
    }
}
