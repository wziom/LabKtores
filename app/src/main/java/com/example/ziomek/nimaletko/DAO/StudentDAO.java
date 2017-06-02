package com.example.ziomek.nimaletko.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.example.ziomek.nimaletko.Model.Student;
import com.example.ziomek.nimaletko.Table.StudentTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ziomek on 31.05.2017.
 */

public class StudentDAO implements DAO<Student> {
    private static final String INSERT =
            "insert into " + StudentTable.TABLE_NAME
                    + "(" + StudentTable.StudentColumns.FIRSTNAME + ", "
                    + StudentTable.StudentColumns.LASTNAME + ", "
                    + StudentTable.StudentColumns.AGE + ", "
                    + StudentTable.StudentColumns.ALBUM_NUMBER + ") values (?, ?, ?, ?)";
    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;

    public StudentDAO(SQLiteDatabase db) {
        this.db = db;
        insertStatement = db.compileStatement(StudentDAO.INSERT);
    }

    @Override
    public long save(Student entity) {
        insertStatement.clearBindings();
        insertStatement.bindString(1, entity.getFirstname());
        insertStatement.bindString(2, entity.getLastname());
        insertStatement.bindDouble(3, entity.getAge());
        insertStatement.bindLong(4, entity.getAlbumNumber());
        return insertStatement.executeInsert();
    }

    public void update(Student entity) {
        final ContentValues values = new ContentValues();
        values.put(StudentTable.StudentColumns.FIRSTNAME, entity.getFirstname());
        values.put(StudentTable.StudentColumns.LASTNAME, entity.getLastname());
        values.put(StudentTable.StudentColumns.AGE, entity.getAge());
        values.put(StudentTable.StudentColumns.ALBUM_NUMBER, entity.getAlbumNumber());
        db.update(
                StudentTable.TABLE_NAME,
                values,
                BaseColumns._ID + " = ?",
                new String[] {
                        String.valueOf(entity.getId())
                }
        );
    }

    @Override
    public void delete(Student entity) {
        if (entity.getId() > 0) {
            db.delete(
                    StudentTable.TABLE_NAME,
                    BaseColumns._ID + " = ?",
                    new String[]
                            { String.valueOf(entity.getId()) }
            );
        }
    }
    
    @Override
    public Student get(long id) {
        Student Student = null;
        Cursor c =
                db.query(
                        StudentTable.TABLE_NAME,
                        new String[] {
                                BaseColumns._ID,
                                StudentTable.StudentColumns.FIRSTNAME,
                                StudentTable.StudentColumns.LASTNAME,
                                StudentTable.StudentColumns.AGE,
                                StudentTable.StudentColumns.ALBUM_NUMBER },
                        BaseColumns._ID + " = ?",
                        new String[] { String.valueOf(id) },
                        null,
                        null,
                        null,
                        "1"
                );
        if (c.moveToFirst()) {
            Student = this.buildStudentFromCursor(c);
        }
        if (!c.isClosed()) {
            c.close();
        }
        return Student;
    }

    @Override
    public List<Student> getAll() {
        List<Student> list = new ArrayList<Student>();
        Cursor c =
                db.query(
                        StudentTable.TABLE_NAME,
                        new String[] {
                                BaseColumns._ID,
                                StudentTable.StudentColumns.FIRSTNAME,
                                StudentTable.StudentColumns.LASTNAME,
                                StudentTable.StudentColumns.AGE,
                                StudentTable.StudentColumns.ALBUM_NUMBER },
                        null,
                        null,
                        null,
                        null,
                        StudentTable.StudentColumns.LASTNAME,
                        null);
        if (c.moveToFirst()) {
            do {
                Student Student = this.buildStudentFromCursor(c);
                if (Student != null) {
                    list.add(Student);
                }
            } while (c.moveToNext());
        }
        if (!c.isClosed()) {
            c.close();
        }
        return list;
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

    public Student find(String lastname) {
        Cursor c =
                db.query(
                        StudentTable.TABLE_NAME,
                        new String[] {
                                BaseColumns._ID,
                                StudentTable.StudentColumns.FIRSTNAME,
                                StudentTable.StudentColumns.LASTNAME,
                                StudentTable.StudentColumns.AGE,
                                StudentTable.StudentColumns.ALBUM_NUMBER },
                        StudentTable.StudentColumns.LASTNAME + " = ?",
                        new String[] { lastname },
                        null,
                        null,
                        null,
                        "1");
        if (c.moveToFirst()) {
            Student Student = this.buildStudentFromCursor(c);
            if (Student != null) {
                return Student;
            }
        }
        return null;
    }
}
