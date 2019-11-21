package com.mynuex.travelwishlistroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
// Adapter creates and binds ViewHolders.
// Holds description & priority of a task to RecyclerView to efficiently display data
public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {
    // Class variable to hold list
    private List<Place> mPlaces;

    private WishListClickListener mListener;
    // Constructor created from WishListFragment
    public WishListAdapter(List<Place> places, WishListFragment listener) {
        mPlaces = places;
        this.mListener = listener;

    }
    // Method from WishListFragment to notify of changes
    // Then notifies the adapter to use new values
    public void setPlaces(List<Place> places) {
        mPlaces = places;
        notifyDataSetChanged();
    }

    public class WishListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        LinearLayout layout;
        TextView nameTextView;
        TextView dataCreatedTextView;
        WishListClickListener listener;
        TextView reasonTextView;

        WishListViewHolder(LinearLayout layout, WishListClickListener listener) {
            super(layout);
            this.listener = listener;
            this.layout = layout;
            nameTextView = layout.findViewById(R.id.placeNameTextView);
            dataCreatedTextView = layout.findViewById(R.id.dataCreatedTextView);
            reasonTextView = layout.findViewById(R.id.reasonTextView);

            layout.setOnClickListener(this);
            layout.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View view) {
            // Notify the listener of the event, and which item was clicked
            listener.onListClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick (View view) {
            // Notify the listener of the event, and which item was long-clicked
            listener.onListLongClick(getAdapterPosition());
            return true; // indicates event is consumed, no further processing.
            // if this is false, in this app, the click event is fired too.
        }
    }

    @NonNull
    @Override
    public WishListAdapter.WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get a reference to the wish_list_element TextView and inflate in, in this context
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_element, parent, false);
        // Create a new viewHolder, to contain this TextView
        WishListViewHolder viewHolder = new WishListViewHolder(layout, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.WishListViewHolder holder, int position) {
        // Configures a ViewHolder to display the data for the given position
        // In Android terminology, bind the view and it's data
        Place place = mPlaces.get(position);
        holder.nameTextView.setText(place.getName());
        holder.dataCreatedTextView.setText("Added on: " + place.getDate());
        holder.reasonTextView.setText("Reason: " + place.getReason());
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }
}
