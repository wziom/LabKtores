package com.example.ziomek.nimaletko.Table;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Ziomek on 31.05.2017.
 */

public final class StudentProjectGroupTable {
    public static final String TABLE_NAME = "student_project_group";
    public static class StudentProjectGroupColumns {
        public static final String STUDENT_ID = "student_id";
        public static final String PROJECT_GROUP_ID = "project_group_id";
    }
    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + StudentProjectGroupTable.TABLE_NAME + " (");
        ;
        sb.append(StudentProjectGroupColumns.STUDENT_ID + " LONG NOT NULL, ");
        sb.append(StudentProjectGroupColumns.PROJECT_GROUP_ID + " LONG NOT NULL, ");
        sb.append("FOREIGN KEY(" + StudentProjectGroupColumns.STUDENT_ID + ") REFERENCES " + StudentTable.TABLE_NAME + "("
                + BaseColumns._ID + "), ");
        sb.append("FOREIGN KEY(" +
                        StudentProjectGroupColumns.PROJECT_GROUP_ID + ") REFERENCES " + ProjectGroupTable.TABLE_NAME + "("
                + BaseColumns._ID + ") , ");
        sb.append("PRIMARY KEY ( " + StudentProjectGroupColumns.STUDENT_ID + ", "
                + StudentProjectGroupColumns.PROJECT_GROUP_ID + ")");
        sb.append(");");
        db.execSQL(sb.toString());
    }
    public static void onUpgrade(SQLiteDatabase db, int oldVersion,
                                 int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + StudentProjectGroupTable.TABLE_NAME);
        StudentProjectGroupTable.onCreate(db);
    }
}