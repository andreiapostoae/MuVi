package com.samaras.muvi;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.lang.UProperty;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    DatabaseReference rf = FirebaseDatabase.getInstance().getReference("users");
    public static String e_mail;

    private void signIn(String email, String password) {
        e_mail = new String(email);
        Log.d(TAG, "signIn:" + email);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Authentication succesful.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent switchIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(switchIntent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUI(findViewById(R.id.parent));

        mAuth = FirebaseAuth.getInstance();

        final EditText email = (EditText) findViewById(R.id.editTextEmail);
        final EditText parola = (EditText) findViewById(R.id.editTextParola);


        Button buttonCr = (Button) findViewById(R.id.buttonCreateUser);
        Button buttonLg = (Button) findViewById(R.id.buttonLogIn);
        Button buttonRp = (Button) findViewById(R.id.buttonResetPassword);

        buttonRp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    final String emailRequest = email.getText().toString();

                    Toast.makeText(getApplicationContext(), "Reset password request sent to " + emailRequest,
                            Toast.LENGTH_LONG).show();
                    mAuth.sendPasswordResetEmail(emailRequest);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Wrong email" ,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonCr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final String stringEmail = email.getText().toString();
                    final String stringParola = parola.getText().toString();
                    mAuth.createUserWithEmailAndPassword(stringEmail, stringParola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Log.i("ceva intr", "vceva" );
                                rf.child(mAuth.getCurrentUser().getUid().toString()).setValue(new UserObj(stringEmail));
                                signIn(stringEmail, stringParola);
                            }
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final String stringEmail = email.getText().toString();
                    final String stringParola = parola.getText().toString();

                    signIn(stringEmail, stringParola);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void setupUI(View view) {
        if (view == null) {
            return;
        }

        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Utils.hideKeyboard(LoginActivity.this);
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}
