package com.example.shazly.piazyapp.Notifications;

import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.User;

/**
 * Created by shazly on 27/01/18.
 */

public  class Notification {
    User user;
    Course course;
 String content;

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public Course getCourse() {
        return course;
    }

    public Notification() {

    }

    public Notification(User user, Course course) {
        this.user = user;
        this.course = course;
    }


    protected boolean isInstructor(User user, Course course) {
        for (int i = 0; i < course.getInstructorsId().size(); i++)
            if (course.getInstructorsId().get(i).equals(user.getUserId()))
                return true;

        return false;
    }

}
