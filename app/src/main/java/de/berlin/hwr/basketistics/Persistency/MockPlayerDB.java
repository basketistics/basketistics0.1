package de.berlin.hwr.basketistics.Persistency;

import java.util.ArrayList;
import java.util.List;

public class MockPlayerDB {

    public class Entry {
        public int playerID = 0;
        public String playerName = "";
        public int playerNumber = 0;
        public String playerDescription = "";

        public Entry(int playerID, String playerName, int playerNumber, String playerDescription) {
            this.playerID = playerID;
            this.playerName = playerName;
            this.playerNumber = playerNumber;
            this.playerDescription = playerDescription;
        }
    }

    public List<Entry> db = new ArrayList<Entry>();

    public MockPlayerDB() {
        this.db.add(new Entry(0, "Player1", 5, "Eveniet tempora sint et et. Nobis illo qui aut quas."));
        this.db.add(new Entry(1, "Player2", 12, "Non illum fugiat recusandae magnam quod molestiae."));
        this.db.add(new Entry(2, "Player3", 3, "Minima ut vitae ut consequatur quis vel."));
        this.db.add(new Entry(3, "Player4", 24, "Necessitatibus minima maxime voluptatem ut possimus consequuntur."));
        this.db.add(new Entry(4, "Player5", 8, "Officiis dignissimos qui id ut eligendi."));
        this.db.add(new Entry(999, "Trainer", 999, "Quam est dolor consequatur sunt vero autem accusantium."));
    }
}
