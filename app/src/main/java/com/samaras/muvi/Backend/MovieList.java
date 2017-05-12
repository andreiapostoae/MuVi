package com.samaras.muvi.Backend;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by apo on 11.05.2017.
 */

public class MovieList {
    public static ArrayList<MovieInfo> movies;
    public static HashMap<Integer, String> genres;

    public MovieList() {
        movies = new ArrayList<>();
        genres = new HashMap<Integer, String>();
        genres.put(28, "Action");
        genres.put(12, "Adventure");
        genres.put(16, "Animation");
        genres.put(35, "Comedy");
        genres.put(80, "Crime");
        genres.put(99, "Documentary");
        genres.put(18, "Drama");
        genres.put(10751, "Family");
        genres.put(14, "Fantasy");
        genres.put(36, "History");
        genres.put(27, "Horror");
        genres.put(10402, "Music");
        genres.put(9648, "Mystery");
        genres.put(10749, "Romance");
        genres.put(878, "Science Fiction");
        genres.put(10770, "TV Movie");
        genres.put(53, "Thriller");
        genres.put(10752, "War");
        genres.put(37, "Western");
    }

    public String getGenre(int id) {
        return genres.get(id);
    }
}
