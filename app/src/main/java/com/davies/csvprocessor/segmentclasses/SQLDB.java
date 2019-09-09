package com.davies.csvprocessor.segmentclasses;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDB extends SQLiteOpenHelper
{
    public SQLDB(Context context) {
        super(context, CommonVariables.DB_NAME, null, CommonVariables.DB_VERSION);
    }

    public SQLDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
