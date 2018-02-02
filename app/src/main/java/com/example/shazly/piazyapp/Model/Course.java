package com.example.shazly.piazyapp.Model;

import com.example.shazly.piazyapp.Notifications.PostNotification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shazly on 27/01/18.
 */

public class Course {
    String name="", code="";
    List<Post> posts = new ArrayList<>();
    List<SourceFiles> files  = new ArrayList<>();

    public List<SourceFiles> getFiles() {
        return files;
    }

    List<String> studentsId = new ArrayList<>();
    List<String> instructorsId = new ArrayList<>();

    public List<String> getStudentsId() {
        return studentsId;
    }

    public List<String> getInstructorsId() {
        return instructorsId;
    }

    public Course() {
    }

    public String getCode() {
        return code;
    }

    public Course(String name, String code, ArrayList<String> students, ArrayList<String> instructors) {

        this.name = name;
        this.code = code;
        this.studentsId = students;
        this.instructorsId = instructors;
    }


    public void addPost(Post post, User user) throws InterruptedException {

        UserManger userManger = new UserManger();
        post.followersID.add(user.getUserId());
        for (int i = 0; i < studentsId.size(); i++) {
            if (!(user.getUserId().equals(studentsId.get(i)))) {
                userManger.findUserByID(studentsId.get(i)).addNotifications(new PostNotification(user ,this));
            }
        }

        for (int i = 0; i < instructorsId.size(); i++) {
            if (!(user.getUserId().equals(instructorsId.get(i)))) {
                userManger.findUserByID(instructorsId.get(i)).addNotifications(new PostNotification(user ,this));
                post.followersID.add(instructorsId.get(i));
            }
        }
        posts.add(post);
    }

    public String getName() {
        return name;
    }

    public List<Post> getPosts() {
        return posts;
    }


}
