package com.samaras.muvi.Backend;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by apo on 11.05.2017.
 */

public class MovieInfo {
    public String title;
    public String description;
    public String genres;
    public String rating;
    public Bitmap bitmap;

    public boolean equals(Object o) {
        return (o instanceof MovieInfo) && (((MovieInfo)o).getTitle()).equals(this.title);
    }

    public int hashCode() {
        return title.hashCode();
    }


    public String getTitle() {
        return title;
    }

    public MovieInfo(String title, String description, String rating, String genres, Bitmap bitmap) {
        this.genres = genres;
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.bitmap = bitmap;
    }


    public void printMovie() {
        System.out.println("------------------------");
        System.out.println("title: " + title);
        System.out.println("description: " + description);
        System.out.println("rating: " + rating);
    }


}
