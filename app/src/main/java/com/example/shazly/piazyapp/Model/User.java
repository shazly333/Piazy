package com.example.shazly.piazyapp.Model;

import com.example.shazly.piazyapp.Notifications.Notification;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shazly on 27/01/18.
 */

public class User {

    String name="";
    String email="";
    List<Course> courses = new ArrayList<Course>();
    List<Notification> notifications = new ArrayList<Notification>();
    String userId="";

    public User(FirebaseUser user, String name) {
        this.userId = user.getUid();
        this.name = name;
        this.email = user.getEmail();
        //courses.add(new Course("ddd","wefwef", null, null));



    }

    public User() {

    }

    public List getNotifications() {
        return notifications;
    }



    public String getEmail() {
        return email;
    }

    public void addNotifications(Notification notification) {
        notifications.add(notification);
    }


    public String getName() {
        return name;
    }

    public List getCourses() {
        return courses;
    }

    public String getUserId() {

        return userId;
    }

    public void update() {
        UserManger manger = new UserManger();
        manger.updateUser(this);

    }
}
