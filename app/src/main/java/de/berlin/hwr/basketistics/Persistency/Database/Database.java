package de.berlin.hwr.basketistics.Persistency.Database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import de.berlin.hwr.basketistics.Persistency.Dao.PlayerDao;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;

@android.arch.persistence.room.Database(entities = {PlayerEntity.class}, version = 0)
public abstract class Database extends RoomDatabase {

    public abstract PlayerDao playerDao();
    private static volatile Database INSTANCE;

    public static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            Database.class,
                            "database")
                        .build();
                }
            }
        }
        return INSTANCE;
    }
}
