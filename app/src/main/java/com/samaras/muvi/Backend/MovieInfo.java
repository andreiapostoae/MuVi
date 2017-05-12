package com.samaras.muvi.Backend;

import java.util.ArrayList;

/**
 * Created by apo on 11.05.2017.
 */

public class MovieInfo {
    String title;
    String description;
    ArrayList<Integer> genres;
    double popularityScore;
    double rating;
    String posterPath;
    int id;
    String releaseDate;

    public MovieInfo(int id, String title, String description, double popularityScore, double rating, String posterPath, String releaseDate) {
        this.id = id;
        this.posterPath = posterPath;
        this.popularityScore = popularityScore;
        this.rating = rating;
        this.title = title;
        this.description = description;
        //this.genres = genres;
        this.releaseDate = releaseDate;
    }

    public void printMovie() {
        System.out.println("------------------------");
        System.out.println("title: " + title);
        System.out.println("description: " + description);
        //System.out.println("genres: " + genres);
        System.out.println("rating: " + rating);
    }


}
