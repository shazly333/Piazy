package com.example.shazly.piazyapp.Model;

import com.example.shazly.piazyapp.Notifications.CommentNotifications;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shazly on 27/01/18.
 */

public class Post {



    List<String> followersID = new ArrayList<>();
    List<Comment> comments = new ArrayList<>();
    String content="";
    String title="";
    String postOwnerId="";
    String postOwnerName="";
    public int id=0;
    public String imageUrl="";
    boolean itsOwnerHasPicture = false;


    public Post() {
    }

    public void setItsOwnerHasPicture(boolean itsOwnerHasPicture) {
        this.itsOwnerHasPicture = itsOwnerHasPicture;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isItsOwnerHasPicture() {
        return itsOwnerHasPicture;
    }

    public String getPostOwnerName() {
        return postOwnerName;
    }

    public List<String> getFollowersID() {
        return followersID;
    }

    public String getPostOwnerId() {
        return postOwnerId;
    }


    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public List<Comment> getComments() {
        return comments;
    }

}
