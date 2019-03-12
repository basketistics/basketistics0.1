package de.berlin.hwr.basketistics.Persistency.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;

@Dao
public interface PlayerDao {

    @Query("SELECT * FROM PLAYERENTITY")
    List<PlayerEntity> getAll();

    @Query("SELECT * FROM playerentity WHERE id IN (:playerIds)")
    List<PlayerEntity> getAllByIds(int[] playerIds);

    @Query("SELECT * FROM PlayerEntity WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    PlayerEntity getByName(String first, String last);

    @Insert
    void insertAll(PlayerEntity... player);

    @Insert
    void insert(PlayerEntity player);

    @Delete
    void delete(PlayerEntity player);

    @Query("SELECT * FROM playerentity WHERE id IN (:playerId)")
    PlayerEntity getPlayerById(int playerId);

    @Query("SELECT * FROM playerentity")
    List<PlayerEntity> getAllPLayers();
}
