package com.example.shazly.piazyapp.Notifications;

import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.Post;
import com.example.shazly.piazyapp.Model.User;

/**
 * Created by shazly on 27/01/18.
 */

public  class Notification {
    String  userName;
    Course course;
 String content;
 String userId;
 Post post = null;

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getUserName() {
        return userName;
    }

    public Course getCourse() {
        return course;
    }

    public Post getPost() {
        return post;
    }

    public Notification() {

    }

    public Notification(String userName, Course course, String userId) {
        this.userName = userName;
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
