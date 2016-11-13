package com.dinesh.ituneschase;

import java.io.Serializable;

/**
 * Created by Dinesh Reddy Pogula on 11/12/16.
 */

public class Track implements Serializable {
    public String trackName;
    public String artworkUrl30;
    public String artistName;
    public String trackPrice;
    public String artworkUrl100;
    public String releaseDate;
    public String collectionPrice;
    public String collectionExplicitness;
    public String trackExplicitness;
    public String trackTimeMillis;
    public String country;
    public String currency;
    public String primaryGenreName;
    public String trackCensoredName;
    public String kind;
    public String wrapperType;
    public String trackId;
    public String artistId;
    public String artistViewUrl;
    public String collectionViewUrl;
    public String trackViewUrl;
    public String previewUrl;

    /**
     * This method creates a Long Description based on the elements.
     *
     * @return
     */
    public String getLogDescription() {
        String desp = "Release Data: " + releaseDate + "\nCollection Price: " + collectionPrice
                + "\nCollection Explicitness: " + collectionExplicitness
                + "\ntrackExplicitness: " + trackExplicitness + "\ntrackTimeMillis: " + trackTimeMillis
                + "\nCountry: " + country + "\nCurrency: " + currency + "\nPrimary Genre name: " + primaryGenreName
                + "\nTrack Censored Name: " + trackCensoredName + "\nWrapper Type: " + wrapperType
                + "\nTrack ID: " + trackId + "\nArtist Id: " + artistId;
        return desp;
    }
}
