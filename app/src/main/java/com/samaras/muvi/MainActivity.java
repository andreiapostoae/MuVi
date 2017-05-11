package com.samaras.muvi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.samaras.muvi.Backend.ClientHTTP;
import com.samaras.muvi.Backend.MovieInfo;
import com.samaras.muvi.Backend.MovieList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Apo on 11-May-17.
 */

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;


    ArrayList<HashMap<String, String>> movieInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieList.movies = new ArrayList<>();


        movieInfos = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        (new JSONAsyncTask(ClientHTTP.createURL("/movie/now_playing"), null)).execute();
    }


    public class JSONAsyncTask extends AsyncTask<String, Void, String> {
        HttpURLConnection urlConnection;
        String URL_STRING;
        JSONObject jsonObject;
        View view;


        public JSONAsyncTask(String URL, View view) {
            this.URL_STRING = URL;
            this.view = view;
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

            } catch (Exception e) {
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
                JSONArray movies = jsonObject.getJSONArray("results");
                for (int i = 0; i < movies.length(); i++) {
                    JSONObject obj = movies.getJSONObject(i);
                    String title = obj.getString("original_title");
                    String description = obj.getString("overview");
                    String release_date = obj.getString("release_date");
                    Double rating = obj.getDouble("vote_average");
                    Double popularity = obj.getDouble("popularity");
                    String path_to_jpg = obj.getString("poster_path");
                    Integer id = obj.getInt("id");
                    ArrayList<Integer> genres = new ArrayList<>();
                    JSONArray genre_ids = obj.getJSONArray("genre_ids");
                    for (int j = 0; j < genre_ids.length(); j++) {
                        int genre_id = genre_ids.getInt(j);
                        genres.add(genre_id);
                    }
                    MovieList.movies.add(new MovieInfo(id, title, description, genres, popularity, rating, path_to_jpg, release_date));

                    HashMap<String, String> movieInfo = new HashMap<>();
                    movieInfo.put("title", title);
                    movieInfo.put("description", description);
                    movieInfo.put("rating", Double.toString(rating));
                    movieInfos.add(movieInfo);
                }

                for (int i = 0; i < MovieList.movies.size(); i++)
                    MovieList.movies.get(i).printMovie();

                ListAdapter adapter = new SimpleAdapter(MainActivity.this, movieInfos, R.layout.list_item, new String[]{"title", "description", "rating"}, new int[]{R.id.title, R.id.description, R.id.rating});
                lv.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}