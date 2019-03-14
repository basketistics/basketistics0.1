package de.berlin.hwr.basketistics.Persistency.Entities;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "user_repo_join",
        primaryKeys = { "eventId"},
        foreignKeys = {
                @ForeignKey(entity = EventEntity.class,
                        parentColumns = "id",
                        childColumns = "eventId")
        })

public class EventJoinEntity {
    //public final int eventTypeId;
    public final int eventId;
    public final int countOnePointer;
    public final int countTwoPointer;
    public final int countThreePointer;
    public final int countOnePointerAttempts;
    public final int countTwoPointerAttempts;
    public final int countThreePointerAttempts;
    public final int countRebounds;
    //public final int[] starterList; //entweder convert in sql speicherbarer typ oder jeder starter einzeln
    public final int countAssists;
    public final int countSteals;
    public final int countBlocks;
    public final int countTurnover;
    public final int countFouls;



    public EventJoinEntity(final int eventId, final int countOnePointer,
                           final int countTwoPointer, final int countThreePointer,
                           final int countOnePointerAttempts, final int countTwoPointerAttempts,
                           final int countThreePointerAttempts, final int countRebounds,
                           final int countAssists, final int countSteals, final int countBlocks,
                           final int countTurnover, final int countFouls)
    {
        this.eventId = eventId;
        //his.eventTypeId = eventTypeId;
        this.countOnePointer = countOnePointer;
        this.countTwoPointer = countTwoPointer;
        this.countThreePointer = countThreePointer;
        this.countOnePointerAttempts = countOnePointerAttempts;
        this.countTwoPointerAttempts = countTwoPointerAttempts;
        this.countThreePointerAttempts = countThreePointerAttempts;
       // this.starterList = starterList;
        this.countAssists = countAssists;
        this.countSteals = countSteals;
        this.countBlocks = countBlocks;
        this.countTurnover = countTurnover;
        this.countFouls = countFouls;
        this.countRebounds = countRebounds;
    }

    //public int getEventTypeId() {
     //   return eventTypeId;
    //}

    public int getEventId() {
        return eventId;
    }

    public void setEventTypeId(int eventTypeId){
    }

    public int getCountOnePointer(){return countOnePointer;}

    public int getCountTwoPointer() {
        return countTwoPointer;
    }

    public int getCountThreePointer() {
        return countThreePointer;
    }

    public int getCountOnePointerAttemps() {
        return countOnePointerAttempts;
    }

    public int getCountTwoPointerAttemps() {
        return countTwoPointerAttempts;
    }

    public int getCountThreePointerAttemps() {
        return countThreePointerAttempts;
    }

    //public int[] getStarterList() {
   //     return starterList;
    //}

    public int getCountAssists() {
        return countAssists;
    }

    public int getCountSteals() {
        return countSteals;
    }

    public int getCountBlocks() {
        return countBlocks;
    }

    public int getCountTurnover() {
        return countTurnover;
    }

    public int getCountFouls() {
        return countFouls;
    }

    public int getCountRebounds() {
        return countRebounds;
    }
}
