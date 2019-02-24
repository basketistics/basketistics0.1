package de.berlin.hwr.basketistics.Persistency.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class PlayerEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "number")
    private int number;

    @ColumnInfo(name = "description")
    private String description;

    public PlayerEntity(String lastName, String firstName, int number, String description) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.number = number;
        this.description = description;
    }

    // Getter and Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
