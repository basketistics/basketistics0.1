package de.berlin.hwr.basketistics.Persistency.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;

import de.berlin.hwr.basketistics.Persistency.Dao.EventDao;
import de.berlin.hwr.basketistics.Persistency.Dao.PlayerDao;
import de.berlin.hwr.basketistics.Persistency.Entities.EventEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;

@android.arch.persistence.room.Database(entities = {PlayerEntity.class, EventEntity.class}, version = 2)
@TypeConverters({Converter.class})
public abstract class Database extends RoomDatabase {

    public abstract PlayerDao playerDao();
    public abstract EventDao eventDao();

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
                            .addCallback(databaseCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback databaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(SupportSQLiteDatabase db) {
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

            PlayerEntity player = new PlayerEntity("Nachname", "Vorname", 15, "testtesttest.");
            dao.insertAll(player);

            return null;
        }
    }
}
