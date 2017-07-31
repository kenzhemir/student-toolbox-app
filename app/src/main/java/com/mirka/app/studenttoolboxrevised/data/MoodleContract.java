package com.mirka.app.studenttoolboxrevised.data;

import android.provider.BaseColumns;

/**
 * Created by Miras on 7/28/2017.
 */

public class MoodleContract {

    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_MOODLE_URL = "moodleURL";
        public static final String COLUMN_USER_NAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_TOKEN = "token";
    }
}
