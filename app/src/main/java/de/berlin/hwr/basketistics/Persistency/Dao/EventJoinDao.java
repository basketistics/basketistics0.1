package de.berlin.hwr.basketistics.Persistency.Dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

import de.berlin.hwr.basketistics.Constants;
import de.berlin.hwr.basketistics.Persistency.Entities.EventJoinEntity;

@Dao
public interface EventJoinDao {

    @Insert
    void insert(EventJoinDao eventJoinDao);

    @Query("SELECT player_id FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='STARTER'  AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.match_id=(:matchId)")
    List<Integer> getStarterByMatchId(int matchId);

    //EINWECHSEL

    //AUSWECHSEL

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ONE_POINT' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getOnePointerByPlayerId(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TWO_POINTS' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getTwoPointerByPlayerId(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='THREE_POINTS' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getThreePointerByPlayerId(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ONE_POINT_ATTEMPT' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getOnePointAttemptsByPlayerId(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TWO_POINTS_ATTEMPT' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getTwoPointAttemptsByPlayerId(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='THREE_POINTS_ATTEMPT' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getThreePointAttemptsByPlayerId(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='REBOUND' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getReboundsByPlayerId(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ASSIST' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getAssistsByPlayerId(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='STEAL' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getStealsByPlayerId(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='BLOCK' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getBlocksByPlayerId(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TURNOVER' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getTurnoversByPlayerId(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='FOUL' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getFoulsByPlayerId(int playerId, int matchId);



    @Query("SELECT player_id FROM EventEntity WHERE match_id=(:match_id)")
    List<Integer> getPlayerIdsByMatch(int match_id);


}

