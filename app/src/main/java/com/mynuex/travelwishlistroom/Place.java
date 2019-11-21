package com.mynuex.travelwishlistroom;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Entity(tableName = "place_table")
public class Place{

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private Date date;
    private String reason;

        // Creating new list and assigning values automatically
    public Place(@NonNull String name, String reason){
        this.name = name;
        this.date = new Date();
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    // Return a String that represents the object
    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name=" + name +
                ", reason=" + reason +
                ", date=" + date +
                '}';
    }
}
