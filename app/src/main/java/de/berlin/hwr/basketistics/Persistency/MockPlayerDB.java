package de.berlin.hwr.basketistics.Persistency;

import java.util.ArrayList;
import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.Player;

public class MockPlayerDB {

    public class Entry {
        public Player player;

        public Entry(Player player) {
            this.player = player;
        }
    }

    public List<Entry> db = new ArrayList<Entry>();

    public MockPlayerDB() {
        this.db.add(new Entry(new Player(0, "Player1", 5, "Eveniet tempora sint et et. Nobis illo qui aut quas.")));
        this.db.add(new Entry(new Player(1, "Player2", 12, "Non illum fugiat recusandae magnam quod molestiae.")));
        this.db.add(new Entry(new Player(2, "Player3", 3, "Minima ut vitae ut consequatur quis vel.")));
        this.db.add(new Entry(new Player(3, "Player4", 24, "Necessitatibus minima maxime voluptatem ut possimus consequuntur.")));
        this.db.add(new Entry(new Player(4, "Player5", 8, "Officiis dignissimos qui id ut eligendi.")));
        this.db.add(new Entry(new Player(999, "Trainer", 999, "Quam est dolor consequatur sunt vero autem accusantium.")));
    }
}
