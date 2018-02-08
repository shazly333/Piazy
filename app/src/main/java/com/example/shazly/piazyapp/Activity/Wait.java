package com.example.shazly.piazyapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.shazly.piazyapp.Model.Comment;
import com.example.shazly.piazyapp.Model.Post;
import com.example.shazly.piazyapp.Model.User;
import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Wait extends AppCompatActivity implements ValueEventListener {

    static public boolean postActivity = false;
    int  i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
//        if(postActivity) {
//            postActivity = false;
//            for( i = 0; i < UserManger.currentPost.getComments().size(); i++) {
//                StorageReference rf =FirebaseStorage.getInstance().getReference().child("images/" +UserManger.currentPost.getComments().get(i).getOwnerId());
//                final Comment comment = UserManger.currentPost.getComments().get(i);
//                rf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                       comment.url =(uri.toString());
//                        if( i + 1 == UserManger.currentPost.getComments().size()){
//                            Intent intent = new Intent(Wait.this, Post.class);
//                            startActivity(intent);
//                        }
//                    }
//                });
//
//            }
//        }
    }
    public Activity activity;

    public void onAttach(Activity activity){
        this.activity = activity;
    }
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        UserManger.currentUser = (dataSnapshot.getValue(User.class));
        Intent intent = new Intent(activity, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
