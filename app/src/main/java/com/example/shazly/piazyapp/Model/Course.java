package com.example.shazly.piazyapp.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shazly on 27/01/18.
 */

public class Course {
    String name = "", code = "";
    List<Post> posts = new ArrayList<>();
    List<ResourceFiles> files = new ArrayList<>();
    List<String> studentsId = new ArrayList<>();
    List<String> instructorsId = new ArrayList<>();
   public int id=0;
    public Course(String name, String code, ArrayList<String> students, ArrayList<String> instructors) {

        this.name = name;
        this.code = code;
        this.studentsId = students;
        this.instructorsId = instructors;
    }

    public Course() {
    }

    public int getId() {
        return id;
    }

    public List<ResourceFiles> getFiles() {
        return files;
    }

    public List<String> getStudentsId() {
        return studentsId;
    }

    public List<String> getInstructorsId() {
        return instructorsId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<Post> getPosts() {
        return posts;
    }


}
