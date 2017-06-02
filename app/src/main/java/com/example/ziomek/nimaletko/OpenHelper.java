package com.example.ziomek.nimaletko;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ziomek.nimaletko.DAO.ProjectGroupDAO;
import com.example.ziomek.nimaletko.Model.ProjectGroup;
import com.example.ziomek.nimaletko.R;
import com.example.ziomek.nimaletko.Table.ProjectGroupTable;
import com.example.ziomek.nimaletko.Table.StudentProjectGroupTable;
import com.example.ziomek.nimaletko.Table.StudentTable;

/**
 * Created by Ziomek on 31.05.2017.
 */

public class OpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "projectsDatabase.db";

    private Context context;

    public OpenHelper(final Context context) {
        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onOpen(final SQLiteDatabase db) {
        super.onOpen(db);
    }
    @Override
    public void onCreate(final SQLiteDatabase db) {
        ProjectGroupTable.onCreate(db);
        ProjectGroupDAO projectGroupDao = new ProjectGroupDAO(db);
        String[] groupProjects =
                context.getResources().getStringArray(
                        R.array.tmdb_groupProjects);
        for (String gProject : groupProjects) {
            projectGroupDao.save(new ProjectGroup((long)0, gProject));
        }
        StudentTable.onCreate(db);
        StudentProjectGroupTable.onCreate(db);
    }
    @Override
    public void onUpgrade(final SQLiteDatabase db,
                          final int oldVersion, final int newVersion) {
        StudentProjectGroupTable.onUpgrade(db, oldVersion, newVersion);
        StudentTable.onUpgrade(db, oldVersion, newVersion);
        ProjectGroupTable.onUpgrade(db, oldVersion, newVersion);
    }
}
