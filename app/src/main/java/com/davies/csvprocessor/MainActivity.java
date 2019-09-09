package com.davies.csvprocessor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;

import com.davies.csvprocessor.segmentclasses.CommonVariables;
import com.davies.csvprocessor.segmentclasses.DataProcessor;
import com.davies.csvprocessor.segmentclasses.ExportData;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private final int FILE_RESULT_CODE  =   81;
    private Context context;
    private ProgressBar progressBar;
    private TableLayout tableLayout;
    private CommonVariables commonVariables =   new CommonVariables();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this.getApplicationContext();
        sharedPreferences   =   this.getSharedPreferences("csvprocessor", Context.MODE_PRIVATE);
        editor  =   sharedPreferences.edit();

        progressBar =   findViewById(R.id.mainProgress);
        tableLayout =   findViewById(R.id.datatable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.importfile) {
            this.openExploreFileSystem();

            return true;
        }
        else if(id == R.id.exportfile) {
            this.exportData();
        }

        return super.onOptionsItemSelected(item);
    }

    private void exportData() {
        File filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String filename = filepath.toString()+File.separator+"csv_processor";

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 34);

            CharSequence title = getString(R.string.app_name);
            ExportData exportData = new ExportData(this.context, sharedPreferences.getString("dataitems", ""), progressBar, filename);
            exportData.execute();
        }
        else
        {
            CharSequence title = getString(R.string.app_name);
            ExportData exportData = new ExportData(this.context, sharedPreferences.getString("dataitems", ""), progressBar, filename);
            exportData.execute();
        }

    }

    private void openExploreFileSystem() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");

        startActivityForResult(intent, FILE_RESULT_CODE);
    }

    private void startDataReading(Uri uri) {
        DataProcessor dataProcessor = new DataProcessor(context, uri, progressBar, tableLayout);
        dataProcessor.execute();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(FILE_RESULT_CODE == requestCode && resultCode == Activity.RESULT_OK){
            Uri path = data.getData();
            this.startDataReading(path);
        }
        else {
            Snackbar.make(findViewById(R.id.mainview), "File could not be read", Snackbar.LENGTH_LONG).show();
        }
    }
}
