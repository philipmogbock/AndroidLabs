package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;



public class MainActivity extends AppCompatActivity {
    private EditText enteredEmail;
    private EditText password;
    private Button login;
    public SharedPreferences prefs;
    public String emailToSave;

//    public String email;
//    public static final String SHARED_PREFS = "sharedPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab3);

        enteredEmail = (EditText) findViewById(R.id.email);
        login = (Button) findViewById(R.id.login);

        //store Shared Preference Object in prefs vble
        prefs = getSharedPreferences("Email", Context.MODE_PRIVATE);

        //default values if nothing is typed
        String savedEmail= prefs.getString("email", "");
        enteredEmail.setText(savedEmail);

        //onclick for login button
        login.setOnClickListener(v -> {
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            goToProfile.putExtra("email", emailToSave);
            startActivity(goToProfile);

        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor edit=prefs.edit();
        emailToSave= enteredEmail.getText().toString();
        edit.putString("email", emailToSave);
        edit.commit();
    }

}
