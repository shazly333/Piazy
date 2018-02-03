package com.example.shazly.piazyapp.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by shazly on 02/02/18.
 */

public class PostBuilder {
    boolean firstUpdate = true;
    Post post = new Post();
    public PostBuilder(String content, String title, List<String> followers) {
        post.content = content;
        post.title = title;
        post.followersID = followers;
    }

    public Post buildPost() {
        post.postOwnerId = UserManger.currentUser.getUserId();
        post.postOwnerName = UserManger.currentUser.getName();
        return post;

    }



}
