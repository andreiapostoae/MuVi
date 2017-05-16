package com.samaras.muvi.Backend;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Apo on 17-May-17.
 */

public class Wishlist {
    private static Wishlist instance = null;
    public static HashSet<MovieInfo> list;

    protected Wishlist() {
        list = new HashSet<>();
    }

    public static Wishlist getInstance() {
        if (instance == null)
            instance = new Wishlist();
        return instance;
    }

    public void addMovie(MovieInfo movieInfo) {
        list.add(movieInfo);
    }

    public int size() {
        return list.size();
    }




}
