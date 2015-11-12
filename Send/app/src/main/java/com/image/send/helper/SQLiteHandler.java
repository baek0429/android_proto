package com.image.send.helper;

/**
 * SQLite handler manages the db connection
 * Created by BAEK on 8/23/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.image.send.friend.model.FriendContent;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_LOGIN = "login";

    // Login Table Columns names
    private static final String L_KEY_ID = "id";
    private static final String L_KEY_NAME = "name";
    private static final String L_KEY_EMAIL = "email";
    private static final String L_KEY_UID = "uid";
    private static final String L_KEY_CREATED_AT = "created_at";

    //Friends table name
    private static final String TABLE_FRIENDS = "friends";

    // Friends table column names
    private static final String F_KEY_NAME = "name";
    private static final String F_LAST_SYNC_DATE ="date";
    private static final String F_KEY_ID = "id";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + L_KEY_ID + " INTEGER PRIMARY KEY," + L_KEY_NAME + " TEXT,"
                + L_KEY_EMAIL + " TEXT UNIQUE," + L_KEY_UID + " TEXT,"
                + L_KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        String CREATE_FRIENDS_TABLE = "CREATE TABLE " + TABLE_FRIENDS + "("
                + F_KEY_ID + " INTEGER," + F_KEY_NAME + " TEXT PRIMARY KEY," + F_LAST_SYNC_DATE + " TEXT" + ")";

        db.execSQL(CREATE_FRIENDS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(L_KEY_NAME, name); // Name
        values.put(L_KEY_EMAIL, email); // Email
        values.put(L_KEY_UID, uid); // unique_id
        values.put(L_KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();

        // for unit test purposes
        if(user.isEmpty()){
            user.put("uid","d3a68846-dfab-4481-a997-a7ba910c96f0");
            user.put("name","Gh");
            user.put("email","h");
        }

        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Getting user login status return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void clearUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();
    }

    public void addFriend(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(F_KEY_NAME, name); // Name

        // Inserting Row
        long id = db.insert(TABLE_FRIENDS, null, values);
        db.close(); // Closing database connection

        // add friend instance to the friend content.
        FriendContent.addFriendInstance(name);

        Log.d(TAG, "New Friends synced into sqlite: " + id);
    }

    /**
     * delete frined from sql db
     * @param name
     */
    public void deleteFriend(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = new String[1];
        args[0] = name;
        long id = db.delete(TABLE_FRIENDS, F_KEY_NAME + "=?", args);

        // delete friend instance from the friend content also delete friend from friend content.
        FriendContent.deleteFriendInstance(name);

        Log.d(TAG, id + "th friend deleted from the local db");
    }


    public void clearFriends(){
        SQLiteDatabase db = this.getWritableDatabase();
        // delete all rows
        db.delete(TABLE_FRIENDS,null,null);
        db.close();

        // clear friends list from the friend content
        FriendContent.clear();

    }

    /**
     * parse friends to FriendContent static instances from the local db
     */
    public void parseFriends() {
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String,Cursor> friends = new HashMap<>();
        String selectQuery = "SELECT  * FROM " + TABLE_FRIENDS;

        Cursor cursor = db.rawQuery(selectQuery , null);

        for(cursor.moveToFirst(); !cursor.isAfterLast() ;cursor.moveToNext()) {
            FriendContent.addFriendInstance(cursor.getString(1));
        }
    }
}