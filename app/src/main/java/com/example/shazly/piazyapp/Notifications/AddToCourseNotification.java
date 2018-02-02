package com.example.shazly.piazyapp.Notifications;

import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.User;

/**
 * Created by shazly on 27/01/18.
 */

public class AddToCourseNotification extends Notification {
    public AddToCourseNotification(User user, Course course) {
        super(user, course);
        content =  ("Instructor " + user.getName() + " added you in a course " + course.getName());

    }
public AddToCourseNotification() {

}

}
