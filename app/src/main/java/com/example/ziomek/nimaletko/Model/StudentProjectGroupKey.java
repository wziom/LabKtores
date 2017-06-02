package com.example.ziomek.nimaletko.Model;

/**
 * Created by Ziomek on 01.06.2017.
 */

public class StudentProjectGroupKey {
    private Long studentId;
    private Long projectGroupId;

    public StudentProjectGroupKey(Long studentId, Long projectGroupId) {
        this.setStudentId(studentId);
        this.setProjectGroupId(projectGroupId);
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getProjectGroupId() {
        return projectGroupId;
    }

    public void setProjectGroupId(Long projectGroupId) {
        this.projectGroupId = projectGroupId;
    }
}
