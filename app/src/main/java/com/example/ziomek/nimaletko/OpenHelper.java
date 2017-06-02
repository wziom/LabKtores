package com.example.ziomek.nimaletko;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ziomek.nimaletko.DAO.ProjectGroupDAO;
import com.example.ziomek.nimaletko.DAO.StudentDAO;
import com.example.ziomek.nimaletko.Model.ProjectGroup;
import com.example.ziomek.nimaletko.Model.Student;
import com.example.ziomek.nimaletko.R;
import com.example.ziomek.nimaletko.Table.ProjectGroupTable;
import com.example.ziomek.nimaletko.Table.StudentProjectGroupTable;
import com.example.ziomek.nimaletko.Table.StudentTable;

import java.util.ArrayList;

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
        this.createStudents(db);
        StudentProjectGroupTable.onCreate(db);
    }
    @Override
    public void onUpgrade(final SQLiteDatabase db,
                          final int oldVersion, final int newVersion) {
        StudentProjectGroupTable.onUpgrade(db, oldVersion, newVersion);
        StudentTable.onUpgrade(db, oldVersion, newVersion);
        ProjectGroupTable.onUpgrade(db, oldVersion, newVersion);
    }
    
    private void createStudents(SQLiteDatabase db) {
        StudentDAO studentDao = new StudentDAO(db);
        
        Student student1 = new Student();
        student1.setFirstname("Wojciech");
        student1.setLastname("Ziomek");
        student1.setAge(24);
        student1.setAlbumNumber(105010);
        
        Student student2 = new Student();
        student2.setFirstname("Alfons");
        student2.setLastname("Mucha");
        student2.setAge(157);
        student2.setAlbumNumber(30436);
        
        Student student3 = new Student();
        student3.setFirstname("Vincent");
        student3.setLastname("van Gogh");
        student3.setAge(164);
        student3.setAlbumNumber(32563);
        
        Student student4 = new Student();
        student4.setFirstname("Leonardo");
        student4.setLastname("da Vinci");
        student4.setAge(565);
        student4.setAlbumNumber(1);
        
        Student student5 = new Student();
        student5.setFirstname("Michelangelo");
        student5.setLastname("Buonarroti");
        student5.setAge(542);
        student5.setAlbumNumber(2);

        studentDao.save(student1);
        studentDao.save(student2);
        studentDao.save(student3);
        studentDao.save(student4);
        studentDao.save(student5);
    }
}
