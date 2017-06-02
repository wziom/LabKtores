package com.example.ziomek.nimaletko.DataManager;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ziomek.nimaletko.DAO.ProjectGroupDAO;
import com.example.ziomek.nimaletko.DAO.StudentDAO;
import com.example.ziomek.nimaletko.DAO.StudentProjectGroupDAO;
import com.example.ziomek.nimaletko.Model.ProjectGroup;
import com.example.ziomek.nimaletko.Model.Student;
import com.example.ziomek.nimaletko.OpenHelper;
import com.example.ziomek.nimaletko.Model.StudentProjectGroupKey;

import java.util.List;

/**
 * Created by Ziomek on 31.05.2017.
 */

public class DataManagerImpl implements DataManager {
    private static final int DATABASE_VERSION = 1;
    private Context context;
    private SQLiteDatabase db;
    private ProjectGroupDAO projectGroupDAO;
    private StudentDAO studentDAO;
    private StudentProjectGroupDAO studentProjectGroupDAO;

    public DataManagerImpl(Context context) {
        this.context = context;
        SQLiteOpenHelper openHelper =
                new OpenHelper(this.context);
        db = openHelper.getWritableDatabase();
        projectGroupDAO = new ProjectGroupDAO(db);
        studentDAO = new StudentDAO(db);
        studentProjectGroupDAO = new StudentProjectGroupDAO(db);
    }

    public Student getStudent(long studentId) {
        Student student = studentDAO.get(studentId);
        if (student != null) {
            student.getProjectGroups().addAll(
                    studentProjectGroupDAO.getProjectGroups(student.getId()));
        }
        return student;
    }
    public List<Student> getAllStudents() {
        return studentDAO.getAll();
    }
    public Student findStudent(String name) {
        Student student = studentDAO.find(name);
        if (student != null) {
            student.getProjectGroups().addAll(
                    studentProjectGroupDAO.getProjectGroups(student.getId()));
        }
        return student;
    }
    public long saveStudent(Student student) {
        long studentId = 0L;
        try {
            db.beginTransaction();
            studentId = studentDAO.save(student);
            if (student.getProjectGroups().size() > 0) {
                for (ProjectGroup c : student.getProjectGroups()) {
                    long catId = 0L;
                    ProjectGroup dbCat = projectGroupDAO.find(c.getName());
                    if (dbCat == null) {
                        catId = projectGroupDAO.save(c);
                    } else {
                        catId = dbCat.getId();
                    }
                    StudentProjectGroupKey mcKey =
                            new StudentProjectGroupKey(studentId, catId);
                    if (!studentProjectGroupDAO.exists(mcKey)) {
                        studentProjectGroupDAO.save(mcKey);
                    }
                }
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("MainActivity",
                    "Błąd przy zapisie filmu (transakcję anulowano)", e);
            studentId = 0L;
        } finally {
            db.endTransaction();
        }
        return studentId;
    }
    public boolean deleteStudent(long studentId) {
        boolean result = false;
        try {
            db.beginTransaction();
            Student student = getStudent(studentId);
            if (student != null) {
                for (ProjectGroup c : student.getProjectGroups()) {
                    studentProjectGroupDAO.delete(
                            new StudentProjectGroupKey(student.getId(), c.getId()));
                }
                studentDAO.delete(student);
            }
            db.setTransactionSuccessful();
            result = true;
        } catch (SQLException e) {
            Log.e("[ERROR]",
                    "Błąd przy usuwaniu filmu (transakcję anulowano)", e);
        } finally {
            db.endTransaction();
        }
        return result;
    }
    public ProjectGroup getProjectGroup(long projectGroupId) {
        return projectGroupDAO.get(projectGroupId);
    }
    public List<ProjectGroup> getAllCategories() {
        return projectGroupDAO.getAll();
    }
    public ProjectGroup findProjectGroup(String name) {
        return projectGroupDAO.find(name);
    }
    public long saveProjectGroup(ProjectGroup projectGroup) {
        return projectGroupDAO.save(projectGroup);
    }
    public void deleteProjectGroup(ProjectGroup projectGroup) {
        projectGroupDAO.delete(projectGroup);
    }
// Klasę wewnętrzną OpenHelper znajdziesz na listingu 7.7.
}