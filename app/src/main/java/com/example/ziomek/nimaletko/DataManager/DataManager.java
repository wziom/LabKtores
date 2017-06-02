package com.example.ziomek.nimaletko.DataManager;

import com.example.ziomek.nimaletko.Model.ProjectGroup;
import com.example.ziomek.nimaletko.Model.Student;

import java.util.List;

/**
 * Created by Ziomek on 31.05.2017.
 */

interface DataManager {
    public Student getStudent(long studentId);
    public List<Student> getAllStudents();
    public Student findStudent(String name);
    public long saveStudent(Student student);
    public boolean deleteStudent(long studentId);

    public ProjectGroup getProjectGroup(long projectGroupId);
    public List<ProjectGroup> getAllCategories();
    public ProjectGroup findProjectGroup(String name);
    public long saveProjectGroup(ProjectGroup projectGroup);
    public void deleteProjectGroup(ProjectGroup projectGroup);
}