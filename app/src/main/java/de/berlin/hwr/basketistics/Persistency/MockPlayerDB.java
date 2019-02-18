package de.berlin.hwr.basketistics.Persistency;

import java.util.ArrayList;
import java.util.List;

public class MockPlayerDB {

    class Entry {
        int playerID = 0;
        String playerName = "";
        int playerNumber = 0;

        public Entry(int playerID, String playerName, int playerNumber) {

        }
    }

    List<Entry> db = new ArrayList<Entry>();

    public MockPlayerDB() {
        this.db.add(new Entry(0, "Player1", 1));
        this.db.add(new Entry(1, "Player2", 2));
        this.db.add(new Entry(2, "Player3", 3));
        this.db.add(new Entry(3, "Player4", 4));
        this.db.add(new Entry(4, "Player5", 5));
        this.db.add(new Entry(999, "Trainer", 999));
    }
}
