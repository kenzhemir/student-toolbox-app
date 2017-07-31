package com.mirka.app.studenttoolboxrevised.utils;

import android.content.ContentValues;

import com.mirka.app.studenttoolboxrevised.data.MoodleContract;

/**
 * Created by Miras on 7/31/2017.
 */

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
