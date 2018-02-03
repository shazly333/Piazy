package com.example.shazly.piazyapp.Model;

import com.example.shazly.piazyapp.Notifications.CommentNotifications;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shazly on 27/01/18.
 */

public class Post {



    List<String> followersID = new ArrayList<>();
    List<String> comments = new ArrayList<>();
    String content="";
    String title="";
    String postOwnerId="";
    String postOwnerName="";
   public int id=0;

    public Post() {
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

  /*  public void addComment(String content, User user) throws InterruptedException {
         UserManger manger = new UserManger();
         postOwnerId = user.getUserId();
        for (int i = 0; i < followersID.size(); i++) {
            if (!(user.getUserId().equals( followersID.get(i)))) {
                manger.AddCourseToFollowers(followersID.get(i)).addNotifications(new CommentNotifications(user, UserManger.currentCourse));
            }
        }

        comments.add(content);
        followersID.add(user.getUserId());

    }
*/

    public List<String> getComments() {
        return comments;
    }

}
