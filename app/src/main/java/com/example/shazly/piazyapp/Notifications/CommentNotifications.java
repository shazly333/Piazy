package com.example.shazly.piazyapp.Notifications;

import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.User;

/**
 * Created by shazly on 27/01/18.
 */

public class CommentNotifications extends Notification {
    public CommentNotifications(User user, Course course) {
        super(user, course);
        if (isInstructor(this.user, this.course)) {
            content =  ("Instructor " + user.getName() + " commented in  a post you follow in " + course.getName());
        } else
            content =  (user.getName() + " commented in  a post you follow in " + course.getName());
    }

    public CommentNotifications() {
    }


}
