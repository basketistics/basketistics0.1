package de.berlin.hwr.basketistics.Persistency.Entities;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "user_repo_join",
        primaryKeys = { "eventId", "eventTypeId" },
        foreignKeys = {
                @ForeignKey(entity = EventTypeEntity.class,
                        parentColumns = "eventId",
                        childColumns = "eventTypeId"),
                @ForeignKey(entity = EventEntity.class,
                        parentColumns = "id",
                        childColumns = "eventId")
        })

public class EventJoinEntity {
    public final int eventTypeId;
    public final int eventId;
    public final int countOnePointer;
    public final int countTwoPointer;
    public final int countThreePointer;
    public final int countOnePointerAttemps;
    public final int countTwoPointerAttemps;
    public final int countThreePointerAttemps;
    public final int[] starterList;
    public final int countAssists;
    public final int countSteals;
    public final int countBlocks;
    public final int countTurnover;
    public final int countFouls;



    public EventJoinEntity(final int eventId, final int eventTypeId, final int countOnePointer,
                           final int countTwoPointer, final int countThreePointer,
                           final int countOnePointerAttempts, final int countTwoPointerAttempts,
                           final int countThreePointerAttempts, final int[] starterList,
                           final int countAssists, final int countSteals, final int countBlocks,
                           final int countTurnover, final int countFouls)
    {
        this.eventId = eventId;
        this.eventTypeId = eventTypeId;
        this.countOnePointer = countOnePointer;
        this.countTwoPointer = countTwoPointer;
        this.countThreePointer = countThreePointer;
        this.countOnePointerAttemps = countOnePointerAttempts;
        this.countTwoPointerAttemps = countTwoPointerAttempts;
        this.countThreePointerAttemps = countThreePointerAttempts;
        this.starterList = starterList;
        this.countAssists = countAssists;
        this.countSteals = countSteals;
        this.countBlocks = countBlocks;
        this.countTurnover = countTurnover;
        this.countFouls = countFouls;
    }

    public int getEventTypeId() {
        return eventTypeId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventTypeId(int eventTypeId){
    }

    public int getCountOnePointer(){return countOnePointer;}
}
