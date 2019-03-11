package de.berlin.hwr.basketistics.Persistency.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Dao.EventDao;
import de.berlin.hwr.basketistics.Persistency.Dao.EventTypeDao;
import de.berlin.hwr.basketistics.Persistency.Dao.MatchDao;
import de.berlin.hwr.basketistics.Persistency.Dao.PlayerDao;
import de.berlin.hwr.basketistics.Persistency.Entities.EventEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.EventTypeEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;

@android.arch.persistence.room.Database(
        entities = {
                PlayerEntity.class,
                EventEntity.class,
                MatchEntity.class,
                EventTypeEntity.class},
        version = 14,
        exportSchema = false)
@TypeConverters({Converter.class})
public abstract class Database extends RoomDatabase {

    public abstract PlayerDao playerDao();
    public abstract EventDao eventDao();
    public abstract MatchDao matchDao();
    public abstract EventTypeDao eventTypeDao();

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

        private final EventTypeDao eventTypeDao;

        public PopulateDbAsyncTask(Database db) {
            this.eventTypeDao = db.eventTypeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            eventTypeDao.insert(new EventTypeEntity("GAME_START"              , 0));
            eventTypeDao.insert(new EventTypeEntity("GAME_PAUSE"              , 1));
            eventTypeDao.insert(new EventTypeEntity("FIRST_QUARTER_START"     , 2));
            eventTypeDao.insert(new EventTypeEntity("SECOND_QUARTER_START"    , 3));
            eventTypeDao.insert(new EventTypeEntity("THIRD_QUARTER_START"     , 4));
            eventTypeDao.insert(new EventTypeEntity("FOURTH_QUARTER_START"    , 5));
            eventTypeDao.insert(new EventTypeEntity("FIRST_QUARTER_END"       , 6));
            eventTypeDao.insert(new EventTypeEntity("SECOND_QUARTER_END"      , 7));
            eventTypeDao.insert(new EventTypeEntity("THIRD_QUARTER_END"       , 8));
            eventTypeDao.insert(new EventTypeEntity("FOURTH_QUARTER_END"      , 9));

            eventTypeDao.insert(new EventTypeEntity("STARTER"                 ,10));
            eventTypeDao.insert(new EventTypeEntity("IN"                      ,11));
            eventTypeDao.insert(new EventTypeEntity("OUT"                     ,12));

            eventTypeDao.insert(new EventTypeEntity("POINTS"                  ,20));
            eventTypeDao.insert(new EventTypeEntity("ONE_POINT"               ,21));
            eventTypeDao.insert(new EventTypeEntity("TWO_POINTS"              ,22));
            eventTypeDao.insert(new EventTypeEntity("THREE_POINTS"            ,23));
            eventTypeDao.insert(new EventTypeEntity("ONE_POINT_ATTEMPT"       ,24));
            eventTypeDao.insert(new EventTypeEntity("TWO_POINTS_ATTEMPT"      ,25));
            eventTypeDao.insert(new EventTypeEntity("THREE_POINTS_ATTEMPT"    ,26));
            eventTypeDao.insert(new EventTypeEntity("REBOUND"                 ,30));
            eventTypeDao.insert(new EventTypeEntity("ASSIST"                  ,40));
            eventTypeDao.insert(new EventTypeEntity("STEAL"                   ,50));
            eventTypeDao.insert(new EventTypeEntity("BLOCK"                   ,60));
            eventTypeDao.insert(new EventTypeEntity("TURNOVER"                ,70));
            eventTypeDao.insert(new EventTypeEntity("FOUL"                    ,80));

            return null;
        }
    }
}
