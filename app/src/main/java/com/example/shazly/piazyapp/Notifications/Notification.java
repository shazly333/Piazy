package com.example.shazly.piazyapp.Notifications;

import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.User;

/**
 * Created by shazly on 27/01/18.
 */

public  class Notification {
    String  user;
    Course course;
 String content;
 String userId;

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getUser() {
        return user;
    }

    public Course getCourse() {
        return course;
    }

    public Notification() {

    }

    public Notification(String user, Course course, String userId) {
        this.user = user;
        this.course = course;
        this.userId = userId;
    }


    protected boolean isInstructor(String userId, Course course) {
        for (int i = 0; i < course.getInstructorsId().size(); i++)
            if (course.getInstructorsId().get(i).equals(userId))
                return true;

        return false;
    }

}
