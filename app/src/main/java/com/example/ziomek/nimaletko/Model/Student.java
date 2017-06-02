package com.example.ziomek.nimaletko.Model;

import java.util.Set;

/**
 * Created by Ziomek on 31.05.2017.
 */

public class Student extends ModelBase {
        private String firstname;
        private String lastname;
        private int age;
        private int albumNumber;
        private Set<ProjectGroup> projectGroups;

        public String getFirstname() {
                return firstname;
        }

        public void setFirstname(String firstname) {
                this.firstname = firstname;
        }

        public String getLastname() {
                return lastname;
        }

        public void setLastname(String lastname) {
                this.lastname = lastname;
        }

        public int getAge() {
                return age;
        }

        public void setAge(int age) {
                this.age = age;
        }

        public int getAlbumNumber() {
                return albumNumber;
        }

        public void setAlbumNumber(int albumNumber) {
                this.albumNumber = albumNumber;
        }

        public Set<ProjectGroup> getProjectGroups() {
                return projectGroups;
        }

        public void setProjectGroups(Set<ProjectGroup> projectGroups) {
                this.projectGroups = projectGroups;
        }

        public String toString(){
                return getLastname() + " " + getFirstname() + ", " + getAge() + "l, Nr albumu: " + getAlbumNumber();
        }
// Konstruktor, metody do ustawiania i pobierania wartości oraz
// metody equals i hashCode ze względu na zwięzłość pominięto.
}
