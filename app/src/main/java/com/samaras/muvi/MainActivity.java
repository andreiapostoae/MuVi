package com.samaras.muvi;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.samaras.muvi.Backend.ClientHTTP;
import com.samaras.muvi.Backend.CustomList;
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
import java.util.List;

/**
 * Created by Apo on 11-May-17.
 */

public class MainActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private ListView lv;
    MovieList movieList;

    ArrayList<HashMap<String, String>> movieInfos;

    Drawer result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Utils.setupUI(findViewById(R.id.parent), this);

        //MovieList.movies = new ArrayList<>();
        movieList = new MovieList();

        movieInfos = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

//        new DrawerBuilder().withActivity(this).build();
//        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Trending");
//        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Find me a movie");
//
////create the drawer and remember the `Drawer` result object
//        Drawer result = new DrawerBuilder()
//                .withActivity(this)
//                .addDrawerItems(
//                        item1,
//                        new DividerDrawerItem(),
//                        item2,
//                        new SecondaryDrawerItem().withName("Logout")
//                )
//                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//                    @Override
//                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
//                        // do something with the clicked item :D
//                        System.out.println("ASDSA");
//                        finish();
//                        return true;
//                    }
//                })
//                .build();
//
        PrimaryDrawerItem trendingItem = new PrimaryDrawerItem().withIdentifier(1).withName("Trending")
                .withIcon(GoogleMaterial.Icon.gmd_trending_up);
        PrimaryDrawerItem topRatedItem = new PrimaryDrawerItem().withIdentifier(2).withName("Top Rated")
                .withIcon(GoogleMaterial.Icon.gmd_assessment);

        List<IDrawerItem> categorySubitems = new ArrayList<>();
        SecondaryDrawerItem catAction = new SecondaryDrawerItem().withIdentifier(3).withName("Action").withLevel(2)
                .withIcon(GoogleMaterial.Icon.gmd_flash_on);
        SecondaryDrawerItem catComedy = new SecondaryDrawerItem().withIdentifier(4).withName("Comedy").withLevel(2)
                .withIcon(GoogleMaterial.Icon.gmd_mood);
        SecondaryDrawerItem catThriller = new SecondaryDrawerItem().withIdentifier(5).withName("Thriller").withLevel(2)
                .withIcon(GoogleMaterial.Icon.gmd_gesture);
        SecondaryDrawerItem catHorror = new SecondaryDrawerItem().withIdentifier(6).withName("Horror").withLevel(2)
                .withIcon(GoogleMaterial.Icon.gmd_bug_report);
        categorySubitems.add(catAction);
        categorySubitems.add(catComedy);
        categorySubitems.add(catThriller);
        categorySubitems.add(catHorror);
        PrimaryDrawerItem categoriesItem = new PrimaryDrawerItem().withIdentifier(7)
                .withSubItems(categorySubitems).withName("Categories")
                .withIcon(GoogleMaterial.Icon.gmd_list)
                .withIcon(GoogleMaterial.Icon.gmd_arrow_drop_down);

        PrimaryDrawerItem settingsItem = new PrimaryDrawerItem().withIdentifier(8).withName("Settings ¯\\_(ツ)_/¯")
                .withIcon(GoogleMaterial.Icon.gmd_settings);
        PrimaryDrawerItem logoutItem = new PrimaryDrawerItem().withIdentifier(9).withName("Logout")
                .withIcon(GoogleMaterial.Icon.gmd_exit_to_app)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        finish();
                        // TODO: add firebase logout
                        return false;
                    }
                });

        result = new DrawerBuilder()
                .withActivity(this)
                .addDrawerItems(
                        trendingItem,
                        topRatedItem,
                        categoriesItem,
                        new DividerDrawerItem(),
                        settingsItem,
                        logoutItem)
                .build();

        (new JSONAsyncTask(ClientHTTP.createURL("/movie/now_playing"), null)).execute();
    }


    public class JSONAsyncTask extends AsyncTask<String, Void, String> {
        HttpURLConnection urlConnection;
        String URL_STRING;
        JSONObject jsonObject;
        View view;
        Bitmap[] images;
        String[] titles;
        String[] ratings;
        String[] descriptions;
        String[] genres;
        int current_index = 0;


        public JSONAsyncTask(String URL, View view) {
            this.URL_STRING = URL;
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
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
                titles = new String[movies.length()];
                images = new Bitmap[movies.length()];
                descriptions = new String[movies.length()];
                ratings = new String[movies.length()];
                genres = new String[movies.length()];

                for (int i = 0; i < movies.length(); i++) {
                    JSONObject obj = movies.getJSONObject(i);
                    String title = obj.getString("original_title");
                    String description = obj.getString("overview");
                    String release_date = obj.getString("release_date");
                    Double rating = obj.getDouble("vote_average");
                    Double popularity = obj.getDouble("popularity");
                    String path_to_jpg = obj.getString("poster_path");
                    Integer id = obj.getInt("id");
                    //ArrayList<Integer> genres = new ArrayList<>();
                    JSONArray genre_ids = obj.getJSONArray("genre_ids");
                    //for (int j = 0; j < genre_ids.length(); j++) {
                    //    int genre_id = genre_ids.getInt(j);
                     //   genres.add(genre_id);
                    //}
                    MovieList.movies.add(new MovieInfo(id, title, description, popularity, rating, path_to_jpg, release_date));
                    String photoURLString = ClientHTTP.createPhotoURL(path_to_jpg);
                    System.out.println(photoURLString);



                    HashMap<String, String> movieInfo = new HashMap<>();
                    movieInfo.put("title", title);
                    movieInfo.put("description", description);
                    movieInfo.put("rating", "Average rating: " + Double.toString(rating));
                    movieInfos.add(movieInfo);
                    titles[i] = title;

                    int description_length = description.length();
                    int stop_index = 170;
                    if(description_length >= 170)
                        for(int j = 150; j < 170; j++)
                            if(description.charAt(j) == ' ' || description.charAt(j) == '.')
                                stop_index = j;

                    if(description_length > 170)
                        description = description.substring(0, stop_index);
                    descriptions[i] = description + " [...]";


                    ratings[i] = "Rating: " + Double.toString(rating);
                    genres[i] = "";
                    for (int j = 0; j < Math.min(genre_ids.length(), 3); j++) {
                        genres[i] += movieList.getGenre(genre_ids.getInt(j));
                        if (j != Math.min(genre_ids.length(), 3) - 1)
                            genres[i] += ", ";
                    }

                    new ImageAsync(photoURLString).execute();
                }

                for (int i = 0; i < MovieList.movies.size(); i++)
                    MovieList.movies.get(i).printMovie();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public class ImageAsync extends AsyncTask<String, Void, Bitmap> {
            String photoURLString;

            public ImageAsync(String photoURLString) {
                this.photoURLString = photoURLString;
            }

            @Override
            protected Bitmap doInBackground(String... urls) {
                try {
                    java.net.URL photoURL = new java.net.URL(photoURLString);
                    HttpURLConnection connection = (HttpURLConnection) photoURL
                            .openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    return myBitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                images[current_index] = result;
                current_index++;

                if(pDialog.isShowing())
                    pDialog.dismiss();
                CustomList adapter = new CustomList(MainActivity.this, titles, images, descriptions, ratings, genres);
                lv.setAdapter(adapter);
            }

        }
    }

}