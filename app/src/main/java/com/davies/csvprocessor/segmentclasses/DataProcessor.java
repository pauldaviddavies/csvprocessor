package com.davies.csvprocessor.segmentclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataProcessor extends AsyncTask<String, Void, List<String[]>>
{
    private Context context;
    private Uri uri;
    private List<String[]> list, headers, data;
    private ProgressBar progressBar;
    CommonVariables commonVariables =   new CommonVariables();
    private TableLayout tableLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Gson gson   =   new Gson();


    public DataProcessor(Context context, Uri uri, ProgressBar progressBar, TableLayout tableLayout)
    {
        this.context    =   context;
        this.uri    =   uri;
        this.progressBar = progressBar;
        this.tableLayout = tableLayout;
        list = new ArrayList<>();
        headers = new ArrayList<>();
        data = new ArrayList<>();
        sharedPreferences= context.getSharedPreferences("csvprocessor", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(List<String[]> lists)
    {
        super.onPostExecute(lists);

        commonVariables.setDocData(lists);

        Toast.makeText(context, "Data import completed", Toast.LENGTH_LONG).show();

        for(int i=0; i<lists.size(); i++) {
            TableRow tableRow   =   new TableRow(context);
            TableLayout.LayoutParams tableRowParams= new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
            tableRowParams.setMargins(5,10,5,10);
            tableRow.setLayoutParams(tableRowParams);
            if(i == 0) {
                for(String h : lists.get(i)){
                    TextView textView = new TextView(context);
                    textView.setTextSize(14);
                    textView.setGravity(Gravity.CENTER);
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    params.setMargins(3,3,3,3);
//                    textView.setLayoutParams(params);
                    textView.setText(h);
                    tableRow.addView(textView);
                }
            }
            else {
                for(String d : lists.get(i)){
                    TextView textView = new TextView(context);
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    params.setMargins(3,3,3,3);
//                    textView.setLayoutParams(params);
                    textView.setText(d);
                    textView.setTextSize(12);
                    tableRow.addView(textView);
                }
            }

            tableLayout.addView(tableRow);
        }

        commonVariables.setDocHeaders(headers);
        commonVariables.setDocData(data);

        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    protected List doInBackground(String... strings)
    {
        try {
            InputStream inputStream =   this.context.getContentResolver().openInputStream(uri);
            BufferedReader bufferedReader   =   new BufferedReader(new InputStreamReader(inputStream));
            String data;
            try {
                while((data = bufferedReader.readLine()) != null){
                    String[] row = data.split(",");
                    list.add(row);
                }
            }
            catch (IOException ioe){
                Log.d("PDD Catch", ioe.getMessage());
            }
            finally {
                try{
                    inputStream.close();
                }
                catch (IOException ioe) {
                    Log.d("PDD Finally", ioe.getMessage());
                }
            }
        }
        catch (FileNotFoundException e) {
            Log.d("###File", e.getMessage());
        }

        String mydata = gson.toJson(list);
        editor.putString("dataitems", mydata);
        editor.commit();

        return list;
    }
}
