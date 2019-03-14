package de.berlin.hwr.basketistics.Persistency.Dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

import de.berlin.hwr.basketistics.Constants;
import de.berlin.hwr.basketistics.Persistency.Entities.EventJoinEntity;

@Dao
public interface EventJoinDao {

    //@Insert
    //void insert(EventJoinDao eventJoinDao);

    @Query("SELECT player_id FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='STARTER'  AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.match_id=(:matchId)")
    List<Integer> getStarterByMatchId(int matchId);

    //EINWECHSEL

    //AUSWECHSEL

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ONE_POINT' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getOnePointerByPlayerAndMatch(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TWO_POINTS' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getTwoPointerByPlayerAndMatch(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='THREE_POINTS' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getThreePointerByPlayerAndMatch(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ONE_POINT_ATTEMPT' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getOnePointAttemptsByPlayerAndMatch(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TWO_POINTS_ATTEMPT' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getTwoPointAttemptsByPlayerAndMatch(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='THREE_POINTS_ATTEMPT' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getThreePointAttemptsByPlayerAndMatch(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='REBOUND' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getReboundsByPlayerAndMatch(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ASSIST' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getAssistsByPlayerAndMatch(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='STEAL' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getStealsByPlayerAndMatch(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='BLOCK' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getBlocksByPlayerAndMatch(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TURNOVER' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getTurnoversByPlayerAndMatch(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='FOUL' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getFoulsByPlayerAndMatch(int playerId, int matchId);


    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ONE_POINT' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getOnePointerByPlayer(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TWO_POINTS' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getTwoPointerByPlayer(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='THREE_POINTS' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getThreePointerByPlayer(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ONE_POINT_ATTEMPT' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getOnePointAttemptsByPlayer(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TWO_POINTS_ATTEMPT' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getTwoPointAttemptsByPlayer(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='THREE_POINTS_ATTEMPT' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getThreePointAttemptsByPlayer(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='REBOUND' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getReboundsByPlayer(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ASSIST' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getAssistsByPlayer(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='STEAL' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getStealsByPlayer(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='BLOCK' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getBlocksByPlayer(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TURNOVER' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getTurnoversByPlayer(int playerId, int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='FOUL' AND eventEntity.match_id =(:matchId) AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getFoulsByPlayer(int playerId, int matchId);
    //@Query("SELECT COUNT(player_id) FROM PlayerEntity, EventEntity")



    @Query("SELECT player_id FROM EventEntity WHERE match_id=(:match_id) GROUP BY player_id")
    List<Integer> getPlayerIdsByMatch(int match_id);


}

