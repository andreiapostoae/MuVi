package com.samaras.muvi.Backend;

/**
 * Created by apo on 11.05.2017.
 */

public class ClientHTTP {
    private static final String API_KEY = "f76dfa1acb7d1a736694eec710bd040b";
    private static final String baseLink = "https://api.themoviedb.org/3";
    private static final String keyString = "?api_key=";

    public static String createURL(String requestString) {
        return baseLink + requestString + keyString + API_KEY;
    }

    public static String createPhotoURL(String pathString) {
        return "http://image.tmdb.org/t/p/w185//" + pathString.substring(1);
    }



}
