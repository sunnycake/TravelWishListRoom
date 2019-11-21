package com.mynuex.travelwishlistroom;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Place.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class PlaceDatabase extends RoomDatabase {

    private static final String LOG_TAG = "DATABASE_LOG";

    // Volatile can only be reference from main memory
    private static volatile PlaceDatabase INSTANCE;
    // Abstract method that returns an instance of the PlaceDao interface
    public abstract PlaceDao mPlaceDao();
    // Creating the database object
    static PlaceDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlaceDatabase.class) {
                Log.d(LOG_TAG, "Creating new database instance");
                if (INSTANCE == null) { // If still null then create database reference
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PlaceDatabase.class, "Place").build();
                }
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return INSTANCE;
    }
}
