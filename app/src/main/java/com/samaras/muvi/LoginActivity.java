package com.samaras.muvi;


import android.icu.lang.UProperty;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.samaras.muvi.Backend.ClientHTTP;
import com.samaras.muvi.Backend.MovieList;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference rf = FirebaseDatabase.getInstance().getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        final EditText email = (EditText) findViewById(R.id.editTextEmail);
        final EditText parola = (EditText) findViewById(R.id.editTextParola);

        Button buttonCr = (Button) findViewById(R.id.buttonCreateUser);
        Button buttonLg = (Button) findViewById(R.id.buttonLogIn);


        buttonCr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String stringEmail = email.getText().toString();
                String stringParola = parola.getText().toString();
                mAuth.createUserWithEmailAndPassword(stringEmail, stringParola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Log.i("ceva intr", "vceva" );
                            rf.child(mAuth.getCurrentUser().getUid().toString()).setValue(new UserObj(stringEmail));
                        }
                    }
                });
            }
        });

        buttonLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String stringEmail = email.getText().toString();
                String stringParola = parola.getText().toString();
                mAuth.signInWithEmailAndPassword(stringEmail, stringParola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Toast.makeText(getApplicationContext(), "te-ai logat",
//                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
}
