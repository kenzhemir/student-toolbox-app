package com.mirka.app.studenttoolboxrevised.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mirka.app.studenttoolboxrevised.utils.MoodleUtils;


/**
 * Created by Miras on 7/28/2017.
 */

public class MoodleDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moodle.db";
    private static final int DATABASE_VERSION = 2;

    public MoodleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USER_TABLE_QUERY = "CREATE TABLE " +
                MoodleContract.UserEntry.TABLE_NAME + " (" +
                MoodleContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoodleContract.UserEntry.COLUMN_MOODLE_URL + " TEXT NOT NULL, " +
                MoodleContract.UserEntry.COLUMN_USER_NAME + " TEXT NOT NULL, " +
                MoodleContract.UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL, " +
                MoodleContract.UserEntry.COLUMN_TOKEN + " TEXT NOT NULL" +
                ");";
        db.execSQL(SQL_CREATE_USER_TABLE_QUERY);
        final String SQL_CREATE_COURSES_TABLE_QUERY = "CREATE TABLE " +
                MoodleContract.CourseEntry._ID + " INTEGER PRIMARY KEY AUTINCREMENT, " +
                MoodleContract.CourseEntry.COLUMN_MOODLE_ID + " INTEGER UNIQUE, " +
                MoodleContract.CourseEntry.COLUMN_SHORTNAME + " TEXT NOT NULL, " +
                MoodleContract.CourseEntry.COLUMN_FULLNAME + " TEXT NOT NULL" +
                MoodleContract.CourseEntry.COLUMN_IS_ACTIVE + " INTEGER NOT NULL" +
                ");";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ MoodleContract.UserEntry.TABLE_NAME);
        onCreate(db);
    }


}
