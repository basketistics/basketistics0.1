package de.berlin.hwr.basketistics.Persistency;

public class MockPlayerStatsDB {

    public int[][] stats = new int[5][7];

    public MockPlayerStatsDB() {
        for (int[] players : stats) {
            for (int stat : players) {
                stat = 0;
            }
        }
    }
}
