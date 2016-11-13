package com.dinesh.ituneschase;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Dinesh Reddy Pogula on 11/12/16.
 */

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {
    private Track[] mTracks;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTrackName;
        public TextView mTrackPrice;
        public NetworkImageView mNetworkImageView;
        public Track mTrack;

        public ViewHolder(View v) {
            super(v);
            mNetworkImageView = (NetworkImageView) v.getRootView().findViewById(R.id.lTrackNIV);
            mTrackName = (TextView) v.getRootView().findViewById(R.id.trackNameTV);
            mTrackPrice = (TextView) v.getRootView().findViewById(R.id.trackPriceTV);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mTrack == null) return;
            Intent intent = new Intent(view.getContext(), TrackActivity.class);
            intent.putExtra(TrackActivity.TRACK, mTrack);
            view.getContext().startActivity(intent);
        }
    }

    public TrackAdapter(Track[] tracks) {
        mTracks = tracks;
    }


    @Override
    public TrackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Track track = mTracks[position];
        holder.mTrack = track;
        if (track.trackName != null)
            holder.mTrackName.setText("Name: " + ((track.trackName.length() < 10) ? track.trackName
                    : track.trackName.substring(0, 10)));
        holder.mNetworkImageView.setImageUrl(track.artworkUrl30,
                VolleySearch.getInstance(holder.mNetworkImageView.getContext()).getImageLoader());
        if (track.trackPrice != null)
            holder.mTrackPrice.setText("Price: " + track.trackPrice);
    }

    @Override
    public int getItemCount() {
        return mTracks.length;
    }
}


