package com.example.ziomek.nimaletko.Table;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Ziomek on 31.05.2017.
 */

public final class StudentTable {
    public static final String TABLE_NAME = "student";

    public static class StudentColumns implements BaseColumns {
        public static final String FIRSTNAME = "firstname";
        public static final String LASTNAME = "lastname";
        public static final String AGE = "age";
        public static final String ALBUM_NUMBER = "album_number";
    }

    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + StudentTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " LONG PRIMARY KEY, ");
        sb.append(StudentColumns.FIRSTNAME + " TEXT NOT NULL, ");
        sb.append(StudentColumns.LASTNAME + " TEXT NOT NULL, ");
        sb.append(StudentColumns.AGE + " INTEGER NOT NULL, ");
        sb.append(StudentColumns.ALBUM_NUMBER + " INTEGER UNIQUE NOT NULL, ");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db,
                                 int oldVersion,
                                 int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "
                + StudentTable.TABLE_NAME);
        StudentTable.onCreate(db);
    }
}