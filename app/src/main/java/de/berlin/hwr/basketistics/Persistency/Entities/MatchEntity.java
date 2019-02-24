package de.berlin.hwr.basketistics.Persistency.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MatchEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

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

    // Constructor
    public MatchEntity(String city, String opponent, Boolean isHome) {
        this.city = city;
        this.opponent = opponent;
        this.isHome = isHome;
    }

    // Getter and Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
