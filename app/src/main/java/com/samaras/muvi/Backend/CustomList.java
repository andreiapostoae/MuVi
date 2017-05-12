package com.samaras.muvi.Backend;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.samaras.muvi.R;

/**
 * Created by Apo on 12-May-17.
 */

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final Bitmap[] imageId;
    private final String[] description;
    private final String[] rating;
    private final String[] genres;

    public CustomList(Activity context,
                      String[] web, Bitmap[] imageId, String[] description, String[] rating, String[] genres) {
        super(context, R.layout.list_image_layout, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.description = description;
        this.rating = rating;
        this.genres = genres;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_image_layout, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView txtDescription = (TextView) rowView.findViewById(R.id.description);
        TextView txtRating = (TextView) rowView.findViewById(R.id.rating);
        TextView txtGenres = (TextView) rowView.findViewById(R.id.genres);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);
        txtDescription.setText(description[position]);
        txtRating.setText(rating[position]);
        txtGenres.setText(genres[position]);

        imageView.setImageBitmap(imageId[position]);
        return rowView;
    }
}
