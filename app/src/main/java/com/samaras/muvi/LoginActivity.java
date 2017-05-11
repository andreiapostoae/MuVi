package com.samaras.muvi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.samaras.muvi.Backend.ClientHTTP;
import com.samaras.muvi.Backend.JSONAsyncTask;
import com.samaras.muvi.Backend.MovieInfo;
import com.samaras.muvi.Backend.MovieList;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String URL = ClientHTTP.createURL("/movie/now_playing");
        JSONAsyncTask task = new JSONAsyncTask(URL);
        task.
        MovieList.movies = new ArrayList<MovieInfo>();
        task.execute();
        try {
            task.get(2000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("size: " + MovieList.movies.size());

    }



}
