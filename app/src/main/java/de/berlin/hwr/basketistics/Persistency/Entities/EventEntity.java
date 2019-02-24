package de.berlin.hwr.basketistics.Persistency.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class EventEntity implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "timestamp")
    private Date timestamp;

    @ColumnInfo(name = "event_type")
    private int eventType;

    @ColumnInfo(name = "player_id")
    private int playerId;

    @ColumnInfo(name = "match_id")
    private int match;

    // Constructor
    public EventEntity(int eventType, int playerId, int match) {
        this.timestamp = new Date();
        this.eventType = eventType;
        this.playerId = playerId;
        this.match = match;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayer(int player) {
        this.playerId = player;
    }

    public int getMatch() {
        return match;
    }

    public void setMatch(int match) {
        this.match = match;
    }
}
