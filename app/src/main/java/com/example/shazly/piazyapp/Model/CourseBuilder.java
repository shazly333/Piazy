package com.example.shazly.piazyapp.Model;

import android.os.CountDownTimer;
import android.widget.Toast;

import com.example.shazly.piazyapp.Activity.AddCourseActivity;
import com.example.shazly.piazyapp.Notifications.AddToCourseNotification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by shazly on 31/01/18.
 */

public class CourseBuilder {

    boolean firstUpdate = true;
    private Course course = new Course();

    public Course getCourse() {
        return course;
    }

    public CourseBuilder() {
    }

    public  CourseBuilder (String name, String code, ArrayList<String> students, ArrayList<String> instructors) {
        course.name = name;
        course.code = code;
        course.studentsId = students;
        course.instructorsId = instructors;
    }
    public Course buildCourse()
    {
       return course;
    }



}
