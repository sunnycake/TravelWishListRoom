package com.mynuex.travelwishlistroom;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PlaceRepository {

    private PlaceDao placeDao;
    private LiveData<List<Place>> allPlaces;

    public PlaceRepository(Application application) {
        PlaceDatabase db = PlaceDatabase.getDatabase(application);
        placeDao = db.mPlaceDao();
        allPlaces = placeDao.getAllPlaces();


    }

    // insert record asynchronously (in the background)
    public void insert(Place place) {
        new InsertPlaceAsync(placeDao).execute(place);
    }

    public void update(Place place) {
        new UpdatePlaceAsync(placeDao).execute(place);
    }

    public void delete(Place place) {
        new DeletePlaceAsync(placeDao).execute(place);
    }

    public void deleteByID(int id) {
        new DeletePlaceIDAsync(placeDao).execute(id);
    }


    // Database tasks must run the background, not on the UI thread
    static class InsertPlaceAsync extends AsyncTask<Place, Void, Void> {
        private PlaceDao placeDao;

        private InsertPlaceAsync(PlaceDao placeDao) {
            this.placeDao = placeDao;
        }

        @Override
        protected Void doInBackground(Place... places) {
            placeDao.insert(places[0]);
            return null;
        }
    }

    static class UpdatePlaceAsync extends AsyncTask<Place, Void, Void> {
        private PlaceDao placeDao;

        private UpdatePlaceAsync(PlaceDao placeDao) {
            this.placeDao = placeDao;
        }

        @Override
        protected Void doInBackground(Place... places) {
            placeDao.update(places[0]);
            return null;
        }
    }

    static class DeletePlaceAsync extends AsyncTask<Place, Void, Void> {
        private PlaceDao placeDao;

        private DeletePlaceAsync(PlaceDao placeDao) {
            this.placeDao = placeDao;
        }

        @Override
        protected Void doInBackground(Place... places) {
            placeDao.delete(places[0]);
            return null;
        }
    }

    private static class DeletePlaceIDAsync extends AsyncTask<Integer, Void, Void> {
        private PlaceDao placeDao;

        DeletePlaceIDAsync(PlaceDao placeDao) {
            this.placeDao = placeDao;
        }

        @Override
        protected Void doInBackground(Integer... id) {
            placeDao.deleteById(id[0]);
            return null;
        }
    }

    // LiveData objects watch the data store and update as it changes
    // LiveData also deals w/fetching in the background and notifying the observer
    public LiveData<List<Place>> getAllPlaces() {
        return placeDao.getAllPlaces();
    }
}
