package com.mirka.app.studenttoolboxrevised.data;

import android.provider.BaseColumns;

/**
 * Created by Miras on 7/28/2017.
 */

public class MoodleContract {

    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_MOODLE_URL = "moodle_url";
        public static final String COLUMN_USER_NAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_TOKEN = "token";
    }

    public static final class CourseEntry implements BaseColumns {
        public static final String TABLE_NAME = "course";
        public static final String COLUMN_MOODLE_ID = "moodle_id";
        public static final String COLUMN_SHORTNAME = "shortname";
        public static final String COLUMN_FULLNAME = "fullname";
        public static final String COLUMN_IS_ACTIVE = "active";
    }
}
