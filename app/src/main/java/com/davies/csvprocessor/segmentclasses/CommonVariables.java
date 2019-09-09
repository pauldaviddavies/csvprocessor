package com.davies.csvprocessor.segmentclasses;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class CommonVariables {
    public static final String DB_NAME = "csv_processor";
    public static final int DB_VERSION = 1;
    public static List<String[]> doc_headers = new ArrayList<>();
    public static List<String[]> doc_data = new ArrayList<>();

    public List<String[]> getDocHeaders() {
        return this.doc_headers;
    }

    public void setDocHeaders(List<String[]> doc_headers) {
        this.doc_headers    =   doc_headers;
    }

    public List<String[]> getDocData() {
        return this.doc_data;
    }

    public void setDocData(List<String[]> doc_data) {
        this.doc_data = doc_data;
    }
}
