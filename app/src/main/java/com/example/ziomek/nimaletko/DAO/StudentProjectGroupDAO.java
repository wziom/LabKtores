package com.example.ziomek.nimaletko.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.example.ziomek.nimaletko.Model.ProjectGroup;
import com.example.ziomek.nimaletko.Model.Student;
import com.example.ziomek.nimaletko.Table.ProjectGroupTable;
import com.example.ziomek.nimaletko.Model.StudentProjectGroupKey;
import com.example.ziomek.nimaletko.Table.StudentProjectGroupTable;
import com.example.ziomek.nimaletko.Table.StudentTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ziomek on 31.05.2017.
 */

public class StudentProjectGroupDAO {
    private static final String INSERT =
            "insert into " + StudentTable.TABLE_NAME
                    + "(" + StudentTable.StudentColumns.FIRSTNAME + ", "
                    + StudentTable.StudentColumns.LASTNAME + ", "
                    + StudentTable.StudentColumns.AGE + ", "
                    + StudentTable.StudentColumns.ALBUM_NUMBER + ") values (?, ?, ?, ?)";
    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;

    public StudentProjectGroupDAO(SQLiteDatabase db) {
        this.db = db;
        insertStatement = db.compileStatement(StudentProjectGroupDAO.INSERT);
    }

    public long save(StudentProjectGroupKey StudentProjectGroupKey) {
        insertStatement.clearBindings();
        insertStatement.bindLong(1, StudentProjectGroupKey.getStudentId());
        insertStatement.bindLong(2, StudentProjectGroupKey.getProjectGroupId());
        return insertStatement.executeInsert();
    }

    public void delete(StudentProjectGroupKey StudentProjectGroupKey) {
        if (StudentProjectGroupKey.getStudentId() > 0 && StudentProjectGroupKey.getProjectGroupId() > 0) {
            db.delete(StudentProjectGroupTable.TABLE_NAME,
                    StudentProjectGroupTable.StudentProjectGroupColumns.STUDENT_ID + " = ? AND " +
                    StudentProjectGroupTable.StudentProjectGroupColumns.PROJECT_GROUP_ID + " = ? ",
                    new String[]{
                            String.valueOf(StudentProjectGroupKey.getStudentId()),
                            String.valueOf(StudentProjectGroupKey.getProjectGroupId())
            });
        }
    }

    public List<ProjectGroup> getProjectGroups(long studentId) {
        List<Long> projectGroupsIDs = this.getProjectGroupsIDs(studentId);
        return this.getProjecyGroupsList(projectGroupsIDs);
    }

    public List<Student> getStudents(long projectGroupID) {
        List<Long> studentsIDs = this.getStudentsIDs(projectGroupID);
        return this.getStudentsList(studentsIDs);
    }

    private List<ProjectGroup> getProjecyGroupsList(List<Long> projectGroupsIDs) {
        List<ProjectGroup> list = new ArrayList<>();
        Cursor c =
                db.query(
                        ProjectGroupTable.TABLE_NAME,
                        new String[] {
                                BaseColumns._ID,
                                ProjectGroupTable.ProjectGroupColumns.NAME },
                        BaseColumns._ID + " = ?",
                        new String[] { projectGroupsIDs.toString().replace("[","(").replace("]",")") },
                        null,
                        null,
                        ProjectGroupTable.ProjectGroupColumns.NAME,
                        null);
        if (c.moveToFirst()) {
            do {
                ProjectGroup ProjectGroup = new ProjectGroup(c.getLong(0), c.getString(1));
                list.add(ProjectGroup);
            } while (c.moveToNext());
        }
        if (!c.isClosed()) {
            c.close();
        }
        return list;
    }

    private List<Student> getStudentsList(List<Long> studentsIDs) {
        List<Student> list = new ArrayList<>();
        Cursor c =
                db.query(
                        StudentTable.TABLE_NAME,
                        new String[] {
                                BaseColumns._ID,
                                StudentTable.StudentColumns.FIRSTNAME,
                                StudentTable.StudentColumns.LASTNAME,
                                StudentTable.StudentColumns.AGE,
                                StudentTable.StudentColumns.ALBUM_NUMBER
                        },
                        BaseColumns._ID + " = ?",
                        new String[] { studentsIDs.toString().replace("[","(").replace("]",")") },
                        null,
                        null,
                        StudentTable.StudentColumns.LASTNAME,
                        null);
        if (c.moveToFirst()) {
            do {
                Student Student = new Student();
                Student = this.buildStudentFromCursor(c);
                list.add(Student);
            } while (c.moveToNext());
        }
        if (!c.isClosed()) {
            c.close();
        }
        return list;
    }

    private List<Long> getProjectGroupsIDs(long studentId) {
        List<Long> projectGroupsIDs = new ArrayList<>();
        Cursor c =
            db.query(
                    StudentProjectGroupTable.TABLE_NAME,
                    new String[] {
                            StudentProjectGroupTable.StudentProjectGroupColumns.PROJECT_GROUP_ID },
                    StudentProjectGroupTable.StudentProjectGroupColumns.STUDENT_ID + " = ?",
                    new String[] { String.valueOf(studentId) },
                    null,
                    null,
                    StudentProjectGroupTable.StudentProjectGroupColumns.PROJECT_GROUP_ID,
                    null);

        if (c.moveToFirst()) {
            do {
                if ( c != null) {
                    projectGroupsIDs.add(c.getLong(0));
                }
            } while (c.moveToNext());
        }
        if (!c.isClosed()) {
            c.close();
        }
        return projectGroupsIDs;
    }

    private List<Long> getStudentsIDs(long projectGroupID) {
        List<Long> studentsIDs = new ArrayList<>();
        Cursor c =
            db.query(
                    StudentProjectGroupTable.TABLE_NAME,
                    new String[] {
                            StudentProjectGroupTable.StudentProjectGroupColumns.STUDENT_ID },
                    StudentProjectGroupTable.StudentProjectGroupColumns.PROJECT_GROUP_ID + " = ?",
                    new String[] { String.valueOf(projectGroupID) },
                    null,
                    null,
                    StudentProjectGroupTable.StudentProjectGroupColumns.STUDENT_ID,
                    null);

        if (c.moveToFirst()) {
            do {
                if ( c != null) {
                    studentsIDs.add(c.getLong(0));
                }
            } while (c.moveToNext());
        }
        if (!c.isClosed()) {
            c.close();
        }
        return studentsIDs;
    }

    private Student buildStudentFromCursor(Cursor c) {
        Student Student = null;
        if (c != null) {
            Student = new Student();
            Student.setId(c.getLong(0));
            Student.setFirstname(c.getString(1));
            Student.setLastname(c.getString(2));
            Student.setAge(c.getInt(3));
            Student.setAlbumNumber(c.getInt(4));
        }
        return Student;
    }

    public boolean exists(StudentProjectGroupKey StudentProjectGroupKey) {
        Cursor c =
                db.query(
                        StudentProjectGroupTable.TABLE_NAME,
                        new String[] {
                                StudentProjectGroupTable.StudentProjectGroupColumns.STUDENT_ID,
                                StudentProjectGroupTable.StudentProjectGroupColumns.PROJECT_GROUP_ID },
                        StudentProjectGroupTable.StudentProjectGroupColumns.STUDENT_ID + " = ? AND " +
                        StudentProjectGroupTable.StudentProjectGroupColumns.PROJECT_GROUP_ID + " = ? ",
                        new String[] { StudentProjectGroupKey.getStudentId().toString(), StudentProjectGroupKey.getProjectGroupId().toString() },
                        null,
                        null,
                        null,
                        "1");
        if (c.moveToFirst()) {
            Student Student = this.buildStudentFromCursor(c);
            if (Student != null) {
                return true;
            }
        }
        return false;
    }
}
