package com.example.ziomek.nimaletko.Table;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Ziomek on 31.05.2017.
 */

public final class ProjectGroupTable {

    public static final String TABLE_NAME = "project_group";

    public static class ProjectGroupColumns implements BaseColumns {
        public static final String NAME = "name";
    }

    public static void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + ProjectGroupTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " LONG PRIMARY KEY, ");
        sb.append(ProjectGroupColumns.NAME + " TEXT NOT NULL ");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db,
                                 int oldVersion,
                                 int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "
                + ProjectGroupTable.TABLE_NAME);
        ProjectGroupTable.onCreate(db);
    }
}
