package com.example.ziomek.nimaletko.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.example.ziomek.nimaletko.Model.ProjectGroup;
import com.example.ziomek.nimaletko.Table.ProjectGroupTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ziomek on 31.05.2017.
 */

public class ProjectGroupDAO implements DAO<ProjectGroup> {
    private static final String INSERT =
            "insert into " + ProjectGroupTable.TABLE_NAME
                    + "(" + ProjectGroupTable.ProjectGroupColumns.NAME + ") values (?)";
    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;
    public ProjectGroupDAO(SQLiteDatabase db) {
        this.db = db;
        insertStatement = db.compileStatement(ProjectGroupDAO.INSERT);
    }
    @Override
    public long save(ProjectGroup entity) {
        insertStatement.clearBindings();
        insertStatement.bindString(1, entity.getName());
        return insertStatement.executeInsert();
    }

    public void update(ProjectGroup entity) {
        final ContentValues values = new ContentValues();
        values.put(ProjectGroupTable.ProjectGroupColumns.NAME, entity.getName());
        db.update(
                ProjectGroupTable.TABLE_NAME,
                values,
                BaseColumns._ID + " = ?",
                new String[] {
                        String.valueOf(entity.getId()) });
    }

    @Override
    public void delete(ProjectGroup entity) {
        if (entity.getId() > 0) {
            db.delete(
                    ProjectGroupTable.TABLE_NAME,
                    BaseColumns._ID + " = ?",
                    new String[]
                            { String.valueOf(entity.getId()) });
        }
    }

    @Override
    public ProjectGroup get(long id) {
        ProjectGroup ProjectGroup = null;
        Cursor c =
                db.query(
                        ProjectGroupTable.TABLE_NAME,
                        new String[] {
                                BaseColumns._ID,
                                ProjectGroupTable.ProjectGroupColumns.NAME },
                        BaseColumns._ID + " = ?",
                        new String[] { String.valueOf(id) },
                        null,
                        null,
                        null,
                        "1"
                );
        if (c.moveToFirst()) {
            ProjectGroup = this.buildProjectGroupFromCursor(c);
        }
        if (!c.isClosed()) {
            c.close();
        }
        return ProjectGroup;
    }

    @Override
    public List<ProjectGroup> getAll() {
        List<ProjectGroup> list = new ArrayList<ProjectGroup>();
        Cursor c =
                db.query(
                        ProjectGroupTable.TABLE_NAME,
                        new String[] {
                                BaseColumns._ID, ProjectGroupTable.ProjectGroupColumns.NAME },
                        null,
                        null,
                        null,
                        null,
                        ProjectGroupTable.ProjectGroupColumns.NAME,
                        null);
        if (c.moveToFirst()) {
            do {
                ProjectGroup ProjectGroup = this.buildProjectGroupFromCursor(c);
                if (ProjectGroup != null) {
                    list.add(ProjectGroup);
                }
            } while (c.moveToNext());
        }
        if (!c.isClosed()) {
            c.close();
        }
        return list;
    }
    private ProjectGroup buildProjectGroupFromCursor(Cursor c) {
        ProjectGroup ProjectGroup = null;
        if (c != null) {
            ProjectGroup = new ProjectGroup(c.getLong(0), c.getString(1));
        }
        return ProjectGroup;
    }

    public ProjectGroup find(String name) {
        Cursor c =
                db.query(
                        ProjectGroupTable.TABLE_NAME,
                        new String[] {
                                BaseColumns._ID,
                                ProjectGroupTable.ProjectGroupColumns.NAME },
                        ProjectGroupTable.ProjectGroupColumns.NAME + " = ?",
                        new String[] { name },
                        null,
                        null,
                        null,
                        "1");
        if (c.moveToFirst()) {
            ProjectGroup ProjectGroup = this.buildProjectGroupFromCursor(c);
            if (ProjectGroup != null) {
                return ProjectGroup;
            }
        }
        return null;
    }
}
