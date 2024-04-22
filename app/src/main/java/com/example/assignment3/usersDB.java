package com.example.assignment3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class usersDB {

    private final static String DATABASE_NAME="usersDB";
    private final static String DATABASE_TABLE="usersTable";
    private final int DATABASE_VERSION=1;

    public final static String row_id="_id";
    public final static String row_name="_name";
    public final static String row_username="_username";
    public final static String row_password="_password";

    public Context myContext;

    public usersDB(Context con)
    {
        myContext=con;
    }

    private class DBhelper extends SQLiteOpenHelper {

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
