package de.berlin.hwr.basketistics.Persistency.Database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import de.berlin.hwr.basketistics.Persistency.Dao.PlayerDao;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;

@android.arch.persistence.room.Database(entities = {PlayerEntity.class}, version = 0)
public class Database extends RoomDatabase {

    public abstract PlayerDao;

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
