package com.example.shazly.piazyapp.Notifications;

import com.example.shazly.piazyapp.Activity.AddPostActivity;
import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.User;

/**
 * Created by shazly on 27/01/18.
 */

public class PostNotification extends Notification {
    public PostNotification(String userName, String userId, Course course) {
        super(userName, course, userId);

        if (isInstructor(this.user, this.course)) {
            content = ("Instructor " + userName + " posted in course " + course.getName());
        } else
            content = (userName + " posted in course " + course.getName());
    }

    public PostNotification() {}



}
