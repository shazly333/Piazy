package com.example.shazly.piazyapp.Notifications;

import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.Post;
import com.example.shazly.piazyapp.Model.User;

/**
 * Created by shazly on 27/01/18.
 */

public class CommentNotifications extends Notification {
    public CommentNotifications(String userName, Course course, String userId, Post post) {
        super(userName, course, userId);
        this.post = post;
        if (isInstructor(userId, course)) {
            content =  ("Instructor " + userName + " commented in  a post you follow in " + course.getName());
        } else
            content =  (userName + " commented in  a post you follow in " + course.getName());

    }

    public CommentNotifications() {
    }

    public Post getPost() {
        return post;
    }
}
