package com.example.shazly.piazyapp.Notifications;

import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.User;

/**
 * Created by shazly on 27/01/18.
 */

public class AddFileNotification extends Notification {
    String fileName;
    String filePath;

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }
public AddFileNotification(){

}
    public AddFileNotification(String fileName, String filePath, User user, Course course) {
        super(user, course);
        this.fileName = fileName;
        this.filePath = filePath;
        content =  ("Instructor " + user.getName() + " added a file called " + fileName + " in course " + course.getName());

    }

}
