package com.example.shazly.piazyapp.Notifications;

import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.User;

/**
 * Created by shazly on 27/01/18.
 */

public class PostNotification extends Notification {
    public PostNotification(User user, Course course) {
        super(user, course);

        if (isInstructor(this.user, this.course)) {
            content = ("Instructor " + user.getName() + " posted in course " + course.getName());
        } else
            content = (user.getName() + " posted in course " + course.getName());
    }

    public PostNotification() {}



}
