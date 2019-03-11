package de.berlin.hwr.basketistics.Persistency.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

public class EventTypeEntity {

    @PrimaryKey
    @ColumnInfo(name = "event_id")
    private int eventId;

    @ColumnInfo(name = "event_name")
    private String eventName;
}
