package de.berlin.hwr.basketistics.Persistency.Entities;

import android.arch.lifecycle.LiveData;

import java.util.List;

// TODO: Annotate as room entity.
public class Player {

    private String name;
    private int number;
    private String description;

    public Player(String name, int number, String description) {
        this.name = name;
        this.number = number;
        this.description = description;
    }
}
