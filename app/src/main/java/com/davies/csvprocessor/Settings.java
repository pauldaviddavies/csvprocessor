package com.davies.csvprocessor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    Button button;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context =   this;
        activity    =   this;

        sharedPreferences = this.getSharedPreferences("csvprocessor", Context.MODE_PRIVATE);
        editor  =   sharedPreferences.edit();
        button = (Button)findViewById(R.id.button);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = ((EditText)findViewById(R.id.editText)).getText().toString();
                if(value.trim().length() > 0){
                    editor.putString("delim", value.trim());
                    editor.commit();
                    Snackbar.make(button, "Saved", Snackbar.LENGTH_LONG).show();
                    activity.finish();
                }
                else {
                    ((EditText)findViewById(R.id.editText)).setError("Required field!");
                    ((EditText)findViewById(R.id.editText)).requestFocus();
                }
            }
        });
    }

}
