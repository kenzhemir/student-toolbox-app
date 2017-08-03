package com.mirka.app.studenttoolboxrevised.utils;

import android.content.ContentValues;

import com.mirka.app.studenttoolboxrevised.data.MoodleContract;

public class DatabaseUtils {

    public static final int GENERIC_ERROR = 301;

    public static ContentValues getUserCV(int moodleID, String url, String username, String password, String token, String name, String surname) {
        ContentValues cv = new ContentValues();
        cv.put(MoodleContract.UserEntry.COLUMN_MOODLE_URL, url);
        cv.put(MoodleContract.UserEntry.COLUMN_PASSWORD, password);
        cv.put(MoodleContract.UserEntry.COLUMN_USERNAME, username);
        cv.put(MoodleContract.UserEntry.COLUMN_TOKEN, token);
        cv.put(MoodleContract.UserEntry.COLUMN_MOODLE_ID, moodleID);
        cv.put(MoodleContract.UserEntry.COLUMN_NAME, name);
        cv.put(MoodleContract.UserEntry.COLUMN_SURNAME, surname);
        return cv;
    }

}
