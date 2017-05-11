package com.samaras.muvi.Backend;

import org.json.JSONObject;

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
    JSONObject jsonObject;

    public MovieInfo(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String toString() {
        return jsonObject.toString();
    }

}
