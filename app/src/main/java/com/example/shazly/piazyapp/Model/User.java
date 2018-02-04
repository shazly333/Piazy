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
    List<Course> courses = new ArrayList();
    List<Notification> notifications = new ArrayList<Notification>();
    String userId="";
     boolean hasPicture = true;

    public User(FirebaseUser user, String name) {
        this.userId = user.getUid();
        this.name = name;
        this.email = user.getEmail();
    }

    public User() {

    }

    public void setHasPicture(boolean hasPicture) {
        this.hasPicture = hasPicture;
    }

    public List getNotifications() {
        return notifications;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public List getCourses() {
        return courses;
    }
    public String getUserId() { return userId;  }

    public boolean isHasPicture() {
        return hasPicture;
    }

    public void update() {
        UserManger manger = new UserManger();
        manger.updateUser(this);

    }
}
