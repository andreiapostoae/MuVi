package com.samaras.muvi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Andrei Aciobanitei on 22.05.2017.
 */

public class ProfileActivity extends AppCompatActivity {
    public static Button profileButton, imageButton;
    public static EditText nameTerm, genreTerm, emailTerm;
    public static String eMail, accountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilelayout);
        TextView profileTitle;

        profileTitle = (TextView) findViewById(R.id.profileTitle);
        profileButton = (Button) findViewById(R.id.profileButton);
        imageButton = (Button) findViewById(R.id.imageButton);
        nameTerm = (EditText) findViewById(R.id.nameTerm);
        genreTerm = (EditText) findViewById(R.id.genreTerm);
        emailTerm = (EditText) findViewById(R.id.emailTerm);

        profileTitle.setText("User profile");

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent switchIntent = new Intent(getApplicationContext(), ImageActivity.class);
                    startActivity(switchIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error" ,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eMail = emailTerm.getText().toString();
                accountName = nameTerm.getText().toString();
                Toast.makeText(getApplicationContext(), ImageActivity.selectedImage.toString(),
                        Toast.LENGTH_SHORT).show();
                Intent switchIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(switchIntent);
            }
        });
    }
}
