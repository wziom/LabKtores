package com.example.ziomek.nimaletko.Model;

import java.util.Set;

/**
 * Created by Ziomek on 31.05.2017.
 */

public class ProjectGroup extends ModelBase {
    private String name;

    public ProjectGroup(Long id, String name) {
        this.setId(id);
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
