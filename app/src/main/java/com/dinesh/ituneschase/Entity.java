package com.dinesh.ituneschase;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Created by Dinesh Reddy Pogula on 11/12/16.
 */

public enum Entity {
    allTrack, allArtist, movieArtist, movie, podcastAuthor, podcast, musicArtist, musicTrack,
    album, musicVideo, mix, song, audiobookAuthor, audiobook, shortFilmArtist, shortFilm,
    tvEpisode, tvSeason, software, iPadSoftware, macSoftware, ebook;

    /**
     * This will return String used to display the Entity
     *
     * @return String of entity.
     */
    public String toDisplayString() {
        return this.toString().replaceAll("([A-Z])", " $1").toLowerCase();
    }

    /**
     * This method is to get all the entities available
     *
     * @return ArrayList of Entity
     */
    public static ArrayList<Entity> getAll() {
        return new ArrayList<>(EnumSet.allOf(Entity.class));
    }
}
