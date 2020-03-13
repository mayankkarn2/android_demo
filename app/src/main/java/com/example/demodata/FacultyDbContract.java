package com.example.demodata;

import android.provider.BaseColumns;

public class FacultyDbContract  {

    public static final class FacultyEntry implements BaseColumns {

        public static final String TABLE_NAME = "faculty";
        public static final String COLUMN_SNO = "sno";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ROLE = "role";
        public static final String COLUMN_EXTENSION = "extension";
        public static final String COLUMN_RESIDENCE = "residence";
        public static final String COLUMN_EMAIL = "mail";
        public static final String COLUMN_ROOM = "room";
    }
}

