package com.mirka.app.studenttoolboxrevised.utils;

import android.content.ContentValues;

import com.mirka.app.studenttoolboxrevised.data.MoodleContract;

public class DatabaseUtils {

    public static ContentValues getUserCV(String url, String username, String password, String token) {
        ContentValues cv = new ContentValues();
        cv.put(MoodleContract.UserEntry.COLUMN_MOODLE_URL, url);
        cv.put(MoodleContract.UserEntry.COLUMN_PASSWORD, password);
        cv.put(MoodleContract.UserEntry.COLUMN_USER_NAME, username);
        cv.put(MoodleContract.UserEntry.COLUMN_TOKEN, token);
        return cv;
    }

}
