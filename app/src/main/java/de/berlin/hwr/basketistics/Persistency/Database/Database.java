package de.berlin.hwr.basketistics.Persistency.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Dao.EventDao;
import de.berlin.hwr.basketistics.Persistency.Dao.MatchDao;
import de.berlin.hwr.basketistics.Persistency.Dao.PlayerDao;
import de.berlin.hwr.basketistics.Persistency.Entities.EventEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;

@android.arch.persistence.room.Database(
        entities = {PlayerEntity.class, EventEntity.class, MatchEntity.class},
        version = 13)

@TypeConverters({Converter.class})
public abstract class Database extends RoomDatabase {

    public abstract PlayerDao playerDao();
    public abstract EventDao eventDao();
    public abstract MatchDao matchDao();

    private static volatile Database INSTANCE;

    public static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            Database.class,
                            "database")
                            .fallbackToDestructiveMigration()
                            // TODO: remove all main thread queries!
                            .allowMainThreadQueries()
                            // .addCallback(databaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback databaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private final PlayerDao dao;

        public PopulateDbAsyncTask(Database db) {
            this.dao = db.playerDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            PlayerEntity playerEntity = dao.getPlayerById(5);
            if (playerEntity == null) {
                PlayerEntity[] players = new PlayerEntity[10];
                for (int i = 0; i < 10; i++) {
                    players[i] = new PlayerEntity("" + i, "Spieler", i, "testtesttest.");
                }
                dao.insertAll(players);
            }
            return null;
        }
    }
}
