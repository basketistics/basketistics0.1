package de.berlin.hwr.basketistics.Persistency.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.EventEntity;
import de.berlin.hwr.basketistics.ViewModel.EventViewModel;

@Dao
public interface EventDao {

    @Query("SELECT * FROM EventEntity")
    List<EventEntity> getAll();

    @Query("SELECT * FROM EventEntity WHERE id IN (:eventIds)")
    List<EventEntity> getAllByIds(int[] eventIds);

    @Insert
    void insertAll(EventEntity... eventEntities);

    @Insert
    void insert(EventEntity eventEntity);

    @Query("SELECT * FROM EventEntity WHERE match_id IN (:matchId)")
    List<EventEntity> getPlayerEventsByMatch(int matchId);

    @Query("SELECT * FROM EventEntity WHERE match_id IN (:matchId) AND player_id IN (:playerId)")
    List<EventEntity> getEventsByMatchesAndPlayers(int matchId, int playerId);
}
