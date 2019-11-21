package com.mynuex.travelwishlistroom;

import android.app.Application;
import android.print.PrinterId;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PlaceViewModel extends AndroidViewModel {

    private PlaceRepository mRepository;
    private LiveData<List<Place>> mAllPlaces;

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PlaceRepository(application);
        mAllPlaces = mRepository.getAllPlaces();
    }

    public void insert(Place place) {
        mRepository.insert(place);
    }

    public void update(Place place) {
        mRepository.update(place);
    }

    public void delete(Place place) {
        mRepository.delete(place);
    }

    public void deleteById(int id) {
        mRepository.deleteByID(id);
    }

    public LiveData<List<Place>> getAllPlaces() {
        return mAllPlaces;
    }
}