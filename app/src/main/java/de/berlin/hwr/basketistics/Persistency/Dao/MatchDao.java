package de.berlin.hwr.basketistics.Persistency.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;

@Dao
public interface MatchDao {

    @Query("SELECT * FROM MatchEntity")
    List<MatchEntity> getAll();

    @Query("SELECT * FROM EventEntity WHERE id IN (:matchIds)")
    List<MatchEntity> getAllByIds(int[] matchIds);

    @Insert
    void insertAll(MatchEntity... matchEntities);

    @Insert
    void insert(MatchEntity matchEntity);
}
