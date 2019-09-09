package com.davies.csvprocessor.segmentclasses;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportData extends AsyncTask<String, Void, String>
{
    private ProgressBar progressBar;
    private String data;
    private Context context;
    private String filename;
    Gson gson   =   new Gson();

    public ExportData() {

    }
    public ExportData(Context context, String data, ProgressBar progressBar, String filename) {
        this.progressBar = progressBar;
        this.data = data;
        this.context    =   context;
        this.filename = filename;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);

        this.progressBar.setVisibility(View.GONE);

        Toast.makeText(this.context, "Data export finished", Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(String... lists)
    {
        try{

            File file = new File(this.filename);
            if(!file.exists()) {
                file.mkdir();
                file.createNewFile();
            }

            FileWriter fileWriter   =   new FileWriter(this.filename+File.separator+"mydata.csv");
            try{
                JSONArray jsonArray =   new JSONArray(this.data);
                for(int i=0; i<jsonArray.length(); i++) {
                    JSONArray jsonArray1    =   new JSONArray(jsonArray.getString(i));
                    for(int j=0; j<jsonArray1.length(); j++) {
                        fileWriter.append(jsonArray1.getString(j));
                        fileWriter.append(",");
                    }
                    fileWriter.append("\n");
                }
            }
            catch (JSONException e){

            }
            finally {
                try{
                    fileWriter.close();
                }
                catch (IOException e){

                }
            }
        }
        catch (IOException e) {
            Log.d("###E", e.getMessage());
        }
        return null;
    }
}
