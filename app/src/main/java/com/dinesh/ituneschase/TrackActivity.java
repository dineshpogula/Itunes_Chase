package com.dinesh.ituneschase;

import android.content.Intent;
import android.icu.util.Currency;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class TrackActivity extends AppCompatActivity {

    public static final String TRACK = "track";
    private Track mTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_track);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        mTrack = (Track) intent.getSerializableExtra(TRACK);
        ImageView llTrackIV = (ImageView) findViewById(R.id.llTrackNIV);
        VolleyPicLoad.getInstance(this).loadImage(llTrackIV, mTrack.artworkUrl100);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();
        if (mTrack == null) return;
        ((TextView) findViewById(R.id.tNameTV)).setText(mTrack.trackName);
        ((TextView) findViewById(R.id.tArtistTV)).setText(mTrack.artistName);
        ((TextView) findViewById(R.id.lDespTV)).setText(mTrack.getLogDescription());
        Locale locale = new Locale(mTrack.country);
        String symbol = "";
        if (mTrack.currency != null && mTrack.country != null) {
            Currency currency = Currency.getInstance(mTrack.currency);
            symbol = currency.getSymbol(locale);
        }
        ((TextView) findViewById(R.id.tpriceTV)).setText(mTrack.trackPrice + symbol);
    }
}
