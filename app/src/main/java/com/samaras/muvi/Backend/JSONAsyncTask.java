package com.samaras.muvi.Backend;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by apo on 11.05.2017.
 */

public class JSONAsyncTask extends AsyncTask<String, Void, String> {
    HttpURLConnection urlConnection;
    String URL_STRING;
    JSONObject jsonObject;

    public JSONAsyncTask(String URL) {
        this.URL_STRING = URL;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(URL_STRING);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

        } catch( Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return response.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println(result);
        try {
            jsonObject = new JSONObject(result);
            System.out.println("Converting to JSON was successful.");
            MovieList.movies.add(new MovieInfo(jsonObject));
            System.out.println(MovieList.movies.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
