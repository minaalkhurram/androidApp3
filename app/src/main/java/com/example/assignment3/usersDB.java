package com.example.assignment3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;


import androidx.annotation.Nullable;

import java.util.ArrayList;

public class usersDB {

    private final static String DATABASE_NAME = "usersDB";
    private final static String USERS_TABLE = "usersTable";
    private final static String PASSWORDS_TABLE = "passwordsTable";
    private final int DATABASE_VERSION = 1;

    public final static String row_id = "_id";
    public final static String row_name = "_name";
    public final static String row_username = "_username";
    public final static String row_password = "_password";

    public final static String password_row_id = "_id";
    public final static String password_row_userid = "_userid";
    public final static String password_row_username = "_username";
    public final static String password_row_password = "_password";
    public final static String password_row_url = "_url";
    public final static String password_row_flag = "_deleteFlag";

    public Context myContext;

    private DBhelper myHelper;

    private SQLiteDatabase mydb;

    public usersDB(Context con) {
        myContext = con;
    }

    private class DBhelper extends SQLiteOpenHelper {

        public DBhelper(Context con) {
            super(con, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String userTableQuery = "CREATE TABLE " + USERS_TABLE + "(" +
                    row_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    row_name + " TEXT NOT NULL, " +
                    row_username + " TEXT NOT NULL, " +
                    row_password + " TEXT NOT NULL);";

            String passwordTableQuery = "CREATE TABLE " + PASSWORDS_TABLE + "(" +
                    password_row_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    password_row_userid + " INTEGER NOT NULL, " +
                    password_row_username + " TEXT NOT NULL, " +
                    password_row_password + " TEXT NOT NULL, " +
                    password_row_url + " TEXT NOT NULL, " +
                    password_row_flag + " INTEGER NOT NULL, " +
                    "FOREIGN KEY(" + password_row_userid + ") REFERENCES " + USERS_TABLE + "(" + row_id + "));";

            db.execSQL(userTableQuery);
            db.execSQL(passwordTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Handle database schema upgrades if needed
        }
    }

    public void open() {
        myHelper = new DBhelper(myContext);
        mydb = myHelper.getWritableDatabase();
    }

    public void close() {
        myHelper.close();
    }

    public void addNewUser(String name, String username, String pass) {
        ContentValues values = new ContentValues();
        values.put(row_name, name);
        values.put(row_username, username);
        values.put(row_password, pass);

        mydb.insert(USERS_TABLE, null, values);
    }

    public void addPassword(String myUser, String username, String password, String url) {
        int id = getUserid(myUser);
        ContentValues values = new ContentValues();
        Log.d("Database2", "Username: " + username + ", Password: " + password + ", URL: " + url);



        values.put(password_row_userid, id);
        values.put(password_row_username,username);
        values.put(password_row_password, password);
        values.put(password_row_url,url);
        // 1 means not in bin and 0 means in bin
        values.put(password_row_flag,1);

        mydb.insert(PASSWORDS_TABLE, null, values);
    }

    public boolean usernameExists(String uname) {
        String[] columns = {row_id};

        // Define the selection criteria
        String selection = row_username + "=?";
        String[] selectionArgs = {uname};

        // Execute the query
        Cursor cursor = mydb.query(USERS_TABLE, columns, selection, selectionArgs, null, null, null);

        boolean usernameExists = cursor.getCount() > 0;

        cursor.close();

        return usernameExists;
    }

    private int getUserid(String username) {

        String selection = row_username + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = mydb.rawQuery("SELECT " + row_id + " FROM " + USERS_TABLE + " WHERE " + selection, selectionArgs);

        int userId = -1; // Default value if no user found

        int index=cursor.getColumnIndex(row_id);
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(index);
        }

        cursor.close();

        return userId;
    }


    public boolean verifyUser(String username, String password) {
        String[] columns = {row_id};

        // Define the selection criteria
        String selection = row_username + "=? AND " + row_password + "=?";
        String[] selectionArgs = {username, password};

        // Execute the query
        Cursor cursor = mydb.query(USERS_TABLE, columns, selection, selectionArgs, null, null, null);

        // Check if the query returned any rows
        boolean matchFound = cursor.getCount() > 0;

        cursor.close();

        return matchFound;
    }

    public ArrayList<UserData> getAllUserPasswords(String userName) {
        int myid = getUserid(userName);
        ArrayList<UserData> userPasswords = new ArrayList<>();

        String query = "SELECT " + password_row_username + ", " + password_row_password + ", " + password_row_url +
                " FROM " + PASSWORDS_TABLE +
                " WHERE " + password_row_userid + " = ? AND " +
        password_row_flag + " = 1";
        String[] selectionArgs = {String.valueOf(myid)};

        Cursor cursor = mydb.rawQuery(query, selectionArgs);
        int index1 = cursor.getColumnIndexOrThrow(password_row_username);
        int index2 = cursor.getColumnIndexOrThrow(password_row_password);
        int index3 = cursor.getColumnIndexOrThrow(password_row_url);

        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(index1);
                String password = cursor.getString(index2);
                String url = cursor.getString(index3);

                UserData userData = new UserData(username, password, url);
                userPasswords.add(userData);
                Log.d("Database", "Username: " + username + ", Password: " + password + ", URL: " + url);

            } while (cursor.moveToNext());
        }

       cursor.close();
        return userPasswords;
    }

    public ArrayList<UserData> getrecycleBin(String userName) {
        int myid = getUserid(userName);
        ArrayList<UserData> userPasswords = new ArrayList<>();

        String query = "SELECT " + password_row_username + ", " + password_row_password + ", " + password_row_url +
                " FROM " + PASSWORDS_TABLE +
                " WHERE " + password_row_userid + " = ? AND " +
                password_row_flag + " = 0";
        String[] selectionArgs = {String.valueOf(myid)};

        Cursor cursor = mydb.rawQuery(query, selectionArgs);
        int index1 = cursor.getColumnIndexOrThrow(password_row_username);
        int index2 = cursor.getColumnIndexOrThrow(password_row_password);
        int index3 = cursor.getColumnIndexOrThrow(password_row_url);

        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(index1);
                String password = cursor.getString(index2);
                String url = cursor.getString(index3);

                UserData userData = new UserData(username, password, url);
                userPasswords.add(userData);
                Log.d("Database", "Username: " + username + ", Password: " + password + ", URL: " + url);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return userPasswords;
    }
    public void updatePassword(String oldPassword, String oldUrl, String newUsername, String newPassword, String newUrl) {
        ContentValues values = new ContentValues();
        values.put(password_row_username, newUsername);
        values.put(password_row_password, newPassword);
        values.put(password_row_url, newUrl);

        String selection = password_row_password + "=? AND " + password_row_url + "=?";
        String[] selectionArgs = {oldPassword, oldUrl};

        mydb.update(PASSWORDS_TABLE, values, selection, selectionArgs);
    }
    public void deletePass(String username, String password, String url) {
        ContentValues values = new ContentValues();
        values.put(password_row_flag, 0); // Set delete flag to zero

        String selection = password_row_username + "=? AND " +
                password_row_password + "=? AND " +
                password_row_url + "=?";
        String[] selectionArgs = {username, password, url};

        mydb.update(PASSWORDS_TABLE, values, selection, selectionArgs);
    }
    public void restorePass(String username, String password, String url) {
        ContentValues values = new ContentValues();
        values.put(password_row_flag, 1); // Set delete flag to zero

        String selection = password_row_username + "=? AND " +
                password_row_password + "=? AND " +
                password_row_url + "=?";
        String[] selectionArgs = {username, password, url};

        mydb.update(PASSWORDS_TABLE, values, selection, selectionArgs);
    }

    // Add methods for updating and deleting passwords as needed...

}
