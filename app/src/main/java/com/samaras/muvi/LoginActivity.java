package com.samaras.muvi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.samaras.muvi.Backend.ClientHTTP;
import com.samaras.muvi.Backend.JSONAsyncTask;
import com.samaras.muvi.Backend.MovieList;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MovieList.movies = new ArrayList<>();
        String URL = ClientHTTP.createURL("/movie/now_playing");

        (new JSONAsyncTask(URL, findViewById(android.R.id.content))).execute();




    }



}
