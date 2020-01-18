package com.mynuex.travelwishlistroom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao // For defining the methods that access the database
public interface PlaceDao {

    @Insert
    void insert(Place place);

    @Update
    void update(Place place);

    @Delete
    void delete(Place place);

    @Query("DELETE FROM place_table WHERE id = :id" )
    void deleteById(int id);

    @Query("SELECT * FROM place_table ORDER BY UPPER(name) ASC")
    LiveData<List<Place>> getAllPlaces();


}
