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

    @Query("SELECT player_id FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='STARTER'  AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:matchId)")
    List<Integer> getStarterByMatchId(int matchId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ONE_POINT'  AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getOnePointerByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TWO_POINTS'  AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getTwoPointerByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='THREE_POINTS'  AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getThreePointerByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ONE_POINT_ATTEMPT'  AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getOnePointAttemptsByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TWO_POINTS_ATTEMPT'  AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getTwoPointAttemptsByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='THREE_POINTS_ATTEMPT'  AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getThreePointAttemptsByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='REBOUND' AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getReboundsByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='ASSIST'  AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getAssistsByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='STEAL'  AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getStealsByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='BLOCK'  AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getBlocksByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='TURNOVER'  AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getTurnoversByPlayerId(int playerId);

    @Query("SELECT COUNT(event_id) FROM EventEntity, EventTypeEntity WHERE eventTypeEntity.event_name='FOUL'  AND EventEntity.event_type=EventTypeEntity.event_id AND EventEntity.player_id=(:playerId)")
    EventJoinEntity getFoulsByPlayerId(int playerId);



    @Query("SELECT player_id FROM EventEntity WHERE match_id=(:match_id)")
    List<Integer> getPlayerIdsByMatch(int match_id);
}


    public static final int GAME_START              =  0;
    public static final int GAME_PAUSE              =  1;
    public static final int FIRST_QUARTER_START     =  2;
    public static final int SECOND_QUARTER_START    =  3;
    public static final int THIRD_QUARTER_START     =  4;
    public static final int FOURTH_QUARTER_START    =  5;
    public static final int FIRST_QUARTER_END       =  6;
    public static final int SECOND_QUARTER_END      =  7;
    public static final int THIRD_QUARTER_END       =  8;
    public static final int FOURTH_QUARTER_END      =  9;

    public static final int STARTER                 = 10;
    public static final int IN                      = 11;
    public static final int OUT                     = 12;

    public static final int POINTS                  = 20;
    public static final int ONE_POINT               = 21;
    public static final int TWO_POINTS              = 22;
    public static final int THREE_POINTS            = 23;
    public static final int ONE_POINT_ATTEMPT       = 24;
    public static final int TWO_POINTS_ATTEMPT      = 25;
    public static final int THREE_POINTS_ATTEMPT    = 26;
    public static final int REBOUND                 = 30;
    public static final int ASSIST                  = 40;
    public static final int STEAL                   = 50;
    public static final int BLOCK                   = 60;
    public static final int TURNOVER                = 70;
    public static final int FOUL                    = 80;
