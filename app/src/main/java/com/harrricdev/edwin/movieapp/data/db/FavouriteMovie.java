package com.harrricdev.edwin.movieapp.data.db;

/**
 * Created by edwin on 5/13/17.
 */

public class FavouriteMovie {

    private int id;

    private String title;

    private String number;

    public FavouriteMovie(int id, String title, String number) {
        this.id = id;
        this.title = title;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
