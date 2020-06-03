package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);

        //messages
        String toastMessage = MainActivity.this.getResources().getString(R.string.toast_message);
        String checkedMessage = MainActivity.this.getResources().getString(R.string.checked);
        String uncheckedMessage = MainActivity.this.getResources().getString(R.string.unchecked);
        String switchOnMessage = MainActivity.this.getResources().getString(R.string.switch_on);
        String switchOffMessage = MainActivity.this.getResources().getString(R.string.switch_off);
        String undo = MainActivity.this.getResources().getString(R.string.undo);


    //CHECKBOX
        CheckBox cb= findViewById(R.id.checkBox);

//        //using clickListener
//
//        cb.setOnClickListener(v ->
//                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_LONG).show());

//        using checkedChangeListener
        cb.setOnCheckedChangeListener((buttonView, isChecked) ->
                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show());


        cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                Snackbar.make(cb,checkedMessage, Snackbar.LENGTH_INDEFINITE)
                        .setAction(undo, v -> cb.setChecked(false))
                        .show();
            }
            else{
                Snackbar.make(cb, uncheckedMessage, Snackbar.LENGTH_INDEFINITE)
                        .setAction(undo, v -> cb.setChecked(true))
                        .show();
            }
        });




        //SWITCH
        Switch s= findViewById(R.id.switcher);
        s.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                Snackbar.make(s,switchOnMessage, Snackbar.LENGTH_INDEFINITE)
                        .setAction(undo, v -> s.setChecked(false))
                        .show();
            }
            else{
                Snackbar.make(s, switchOffMessage, Snackbar.LENGTH_INDEFINITE)
                        .setAction(undo, v -> s.setChecked(true))
                        .show();
            }
        });

    }
}