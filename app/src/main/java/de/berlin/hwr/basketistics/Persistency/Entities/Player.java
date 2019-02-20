package de.berlin.hwr.basketistics.Persistency.Entities;

import android.arch.lifecycle.LiveData;

import java.io.Serializable;
import java.util.List;

// TODO: Annotate as room entity.
public class Player implements Serializable {

    public int id;
    public String name;
    public int number;
    public String description;

    public Player(int id, String name, int number, String description) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.description = description;
    }

    public Player(String name, int number, String description) {
        this.name = name;
        this.number = number;
        this.description = description;
    }
}
