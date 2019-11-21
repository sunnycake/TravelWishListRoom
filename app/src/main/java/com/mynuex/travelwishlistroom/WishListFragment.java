package com.mynuex.travelwishlistroom;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WishListFragment extends Fragment implements WishListClickListener{
    // Constant for logging
    private static final String TAG = "WISH_LIST_FRAGMENT";
    // Member variables
    private PlaceViewModel mPlaceViewModel;
    private List<Place> mPlaces;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private WishListAdapter mWishListAdapter;
    // Fields for views
    private Button mAddButton;
    private EditText mNewPlaceNameEditText;
    private EditText mReasonEditText;

    public WishListFragment() {
        // Required empty public constructor
    }

    public static WishListFragment newInstance() {
        WishListFragment wishListFragment = new WishListFragment();
        return wishListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        Context context = view.getContext();

        //mRecyclerView = view.findViewById(R.id.wish_list);
        mAddButton = view.findViewById(R.id.add_place_button);
        mNewPlaceNameEditText = view.findViewById(R.id.new_place_name);
        mReasonEditText = view.findViewById(R.id.reason_text_field);

        // Set RecyclerView to its corresponding view
        mRecyclerView = view.findViewById(R.id.wish_list);
        mRecyclerView.setHasFixedSize(true);
        // Set RecyclerView to be Linear layout
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Initialize the adapter and attach it to the RecyclerView
        mWishListAdapter = new WishListAdapter(mPlaces, this);
        mRecyclerView.setAdapter(mWishListAdapter);
        // Getting View Model instance and use observe to return live data
        mPlaceViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
        mPlaceViewModel.getAllPlaces().observe(this, new Observer<List<Place>>() {
            @Override // Observe for changes
            public void onChanged(List<Place> places) {
                Log.d(TAG, "Places onChanged from LiveData in PlaceViewModel: " + places);
                mPlaces = places;
                mWishListAdapter.setPlaces(places);
                mWishListAdapter.notifyDataSetChanged();
            }
        });
        // Button listeners when clicked to add Place to list
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPlace = mNewPlaceNameEditText.getText().toString();
                String reason = mReasonEditText.getText().toString();
                if (newPlace.isEmpty()){
                    Toast.makeText(WishListFragment.this.getContext(), "You must enter a place. ", Toast.LENGTH_LONG).show();
                    return;
                }
                Place place = new Place(newPlace,reason);
                mPlaces.add(place);
                mPlaceViewModel.insert(place);
                mWishListAdapter.notifyItemInserted(mPlaces.size() -1);
                mNewPlaceNameEditText.getText().clear(); // Clear text fields after adding
                mReasonEditText.getText().clear();
            }
        });
        return view;
    }

    @Override // Method for location map
    public void onListClick(int position) {

        Place place = mPlaces.get(position);
        Uri locationUri = Uri.parse("geo:0,0?q=" + Uri.encode(place.getName())); // Map
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri); // Intent to open map
        startActivity(mapIntent);

    }

    @Override // Method to remove place on longClick
    public void onListLongClick(final int position) {

        final Place place = mPlaces.get(position);

        AlertDialog confirmDeleteDialog = new AlertDialog.Builder(getActivity())
                .setMessage(getString(R.string.delete_place_message, mPlaces.get(position).getName()))
                .setTitle(getString(R.string.delete_dialog_title))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        mPlaceViewModel.delete(place);
                        mWishListAdapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null) // No event handler needed
                .create();
        confirmDeleteDialog.show();
    }

}

