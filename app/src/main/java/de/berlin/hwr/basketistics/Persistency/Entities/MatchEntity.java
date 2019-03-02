package de.berlin.hwr.basketistics.Persistency.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class MatchEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "opponent")
    private String opponent;

    @ColumnInfo(name = "is_home")
    private Boolean isHome;

    @ColumnInfo(name = "points_self")
    private int pointsSelf;

    @ColumnInfo(name = "points_opponent")
    private int pointsOpponent;

    @ColumnInfo(name = "is_winner")
    private Boolean isWinner;

    @ColumnInfo(name = "description")
    private String description;

    // Constructor
    public MatchEntity(String city, String opponent, Boolean isHome, String date, String description) {
        this.city = city;
        this.opponent = opponent;
        this.isHome = isHome;
        this.date = date;
        this.description = description;
    }

    // Getter and Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public Boolean getHome() {
        return isHome;
    }

    public void setHome(Boolean home) {
        isHome = home;
    }

    public int getPointsSelf() {
        return pointsSelf;
    }

    public void setPointsSelf(int pointsSelf) {
        this.pointsSelf = pointsSelf;
    }

    public int getPointsOpponent() {
        return pointsOpponent;
    }

    public void setPointsOpponent(int pointsOpponent) {
        this.pointsOpponent = pointsOpponent;
    }

    public Boolean getWinner() {
        return isWinner;
    }

    public void setWinner(Boolean winner) {
        isWinner = winner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
