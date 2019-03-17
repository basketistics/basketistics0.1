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

    //--------------player stats by game-------------


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

    //--------------global player stats-------------
    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ONE_POINT' AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getOnePointerByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TWO_POINTS' AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getTwoPointerByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='THREE_POINTS' AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getThreePointerByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ONE_POINT_ATTEMPT' AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getOnePointAttemptsByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TWO_POINTS_ATTEMPT' AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getTwoPointAttemptsByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='THREE_POINTS_ATTEMPT' AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getThreePointAttemptsByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='REBOUND' AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getReboundsByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ASSIST' AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getAssistsByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='STEAL' AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getStealsByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='BLOCK' AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getBlocksByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TURNOVER' AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getTurnoversByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='FOUL' AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    int getFoulsByPlayerId(int playerId);

    @Query("SELECT COUNT(DISTINCT match_id) FROM EventEntity WHERE player_id=(:playerId)")
    Integer getGamesPlayedByPlayerId(int playerId);


    //------------global Team stats------------
    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ONE_POINT' AND EventEntity.event_type=EventTypeEntity.event_id")
    int getOnePointer();

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TWO_POINTS' AND EventEntity.event_type=EventTypeEntity.event_id")
    int getTwoPointer();

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='THREE_POINTS' AND EventEntity.event_type=EventTypeEntity.event_id")
    int getThreePointer();

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ONE_POINT_ATTEMPT' AND EventEntity.event_type=EventTypeEntity.event_id")
    int getOnePointAttempts();

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TWO_POINTS_ATTEMPT' AND EventEntity.event_type=EventTypeEntity.event_id")
    int getTwoPointAttempts();

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='THREE_POINTS_ATTEMPT' AND EventEntity.event_type=EventTypeEntity.event_id")
    int getThreePointAttempts();

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='REBOUND' AND EventEntity.event_type=EventTypeEntity.event_id")
    int getRebounds();

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ASSIST' AND EventEntity.event_type=EventTypeEntity.event_id")
    int getAssists();

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='STEAL' AND EventEntity.event_type=EventTypeEntity.event_id")
    int getSteals();

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='BLOCK' AND EventEntity.event_type=EventTypeEntity.event_id")
    int getBlocks();

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TURNOVER' AND EventEntity.event_type=EventTypeEntity.event_id")
    int getTurnovers();

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='FOUL' AND EventEntity.event_type=EventTypeEntity.event_id ")
    int getFouls();

    @Query("SELECT COUNT(DISTINCT match_id) FROM EventEntity ")
    Integer getGamesPlayed();



    @Query("SELECT player_id FROM EventEntity WHERE match_id=(:match_id) GROUP BY player_id")
    List<Integer> getPlayerIdsByMatch(int match_id);





}

