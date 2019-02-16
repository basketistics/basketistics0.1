package de.berlin.hwr.basketistics.Persistency;

import java.util.ArrayList;
import java.util.List;

public class MockEventTypeDB {

    class Entry {
        int eventID = 0;
        String eventName = "";

        public Entry(int eventID, String eventName) {
            this.eventID = eventID;
            this.eventName = eventName;
        }
    }

    private List<Entry> db = new ArrayList<Entry>();

    public MockEventTypeDB() {
        // The eventID should be the same as the list-index
        this.db.add(new Entry(0, "game_begin"));
        this.db.add(new Entry(1, "point_plus_1"));
        this.db.add(new Entry(2, "point_plus_2"));
        this.db.add(new Entry(3, "point_plus_3"));
        this.db.add(new Entry(4, "point_minus_1"));
        this.db.add(new Entry(5, "point_minus_2"));
        this.db.add(new Entry(6, "point_minus_3"));
        this.db.add(new Entry(7, "rebound"));
        this.db.add(new Entry(8, "assist"));
        this.db.add(new Entry(9, "block"));
        this.db.add(new Entry(10, "turnover"));
        this.db.add(new Entry(11, "foul"));
        this.db.add(new Entry(12, "steal"));
        this.db.add(new Entry(13, "game_resume"));
        this.db.add(new Entry(14, "game_pause"));
        this.db.add(new Entry(15, "game_end"));
    }
}
