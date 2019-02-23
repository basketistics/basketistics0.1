package de.berlin.hwr.basketistics.Persistency.Entities;

import android.arch.lifecycle.LiveData;

import java.io.Serializable;
import java.util.List;

// TODO: Annotate as room entity.
public class Player implements Serializable {

    public int id;
    public String lastName;
    public String firstName;
    public int number;
    public String description;

    public Player(int id, String lastName, String firstName, int number, String description) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.number = number;
        this.description = description;
    }

    public Player(String lastName, String firstName, int number, String description) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.number = number;
        this.description = description;
    }
}
