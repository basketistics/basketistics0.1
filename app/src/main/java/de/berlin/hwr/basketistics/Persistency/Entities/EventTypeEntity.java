package de.berlin.hwr.basketistics.Persistency.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class EventTypeEntity {

    public EventTypeEntity(String eventName, Integer id) {
        this.eventName = eventName;
        this.eventId = id;
    }

    @PrimaryKey
    @ColumnInfo(name = "event_id")
    private int eventId;

    @ColumnInfo(name = "event_name")
    private String eventName;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
