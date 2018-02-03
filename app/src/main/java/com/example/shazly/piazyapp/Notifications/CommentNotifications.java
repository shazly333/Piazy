package com.example.shazly.piazyapp.Notifications;

import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.User;

/**
 * Created by shazly on 27/01/18.
 */

public class CommentNotifications extends Notification {
    public CommentNotifications(String user, Course course, String userId) {
        super(user, course, userId);
        if (isInstructor(this.user, this.course)) {
            content =  ("Instructor " + user + " commented in  a post you follow in " + course.getName());
        } else
            content =  (user + " commented in  a post you follow in " + course.getName());
    }

    public CommentNotifications() {
    }


}
