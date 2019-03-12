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

    public EventJoinEntity(final int eventId, final int eventTypeId)
    {
        this.eventId = eventId;
        this.eventTypeId = eventTypeId;
    }

    public int getEventTypeId() {
        return eventTypeId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventTypeId(int eventTypeId){
    }
}
