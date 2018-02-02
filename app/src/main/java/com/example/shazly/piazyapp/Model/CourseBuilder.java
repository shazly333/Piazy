package com.example.shazly.piazyapp.Model;

import java.util.ArrayList;

/**
 * Created by shazly on 31/01/18.
 */

public class CourseBuilder {


    String name, code;
    ArrayList<String> students = new ArrayList<>();
    ArrayList<String> instructors = new ArrayList<>();
    private static CourseBuilder courseBuilder = new CourseBuilder();
    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public ArrayList<String> getInstructors() {
        return instructors;
    }

    public static CourseBuilder getCourseBuilder() {
        return courseBuilder;
    }


   private CourseBuilder(){}

    public static CourseBuilder getInstance(String name, String code,  ArrayList<String> students, ArrayList<String> instructors) {
        courseBuilder.name = name;
        courseBuilder.code = code;
        courseBuilder.students = students;
        courseBuilder.instructors = instructors;
        return courseBuilder;
    }
    public Course buildCourse() {

        Course course = new Course(courseBuilder.name, courseBuilder.code, courseBuilder.students, courseBuilder.instructors);
       return course;
    }
}
