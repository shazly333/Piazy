package com.example.shazly.piazyapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shazly.piazyapp.Adapters.CommentsAdapter;
import com.example.shazly.piazyapp.Adapters.PostsAdapter;
import com.example.shazly.piazyapp.Model.Comment;
import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.Post;
import com.example.shazly.piazyapp.Model.User;
import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.Notifications.AddToCourseNotification;
import com.example.shazly.piazyapp.Notifications.CommentNotifications;
import com.example.shazly.piazyapp.Notifications.PostNotification;
import com.example.shazly.piazyapp.Notifications.RetreiveFeedTask;
import com.example.shazly.piazyapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.bumptech.glide.Glide;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class PostActivity extends AppCompatActivity {

    ListView listOfComments;
    TextView content;
    TextView name;
    ImageView profilePicture;
    ImageButton addComment;
    EditText commentField;
    ProgressDialog wait;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        content = findViewById(R.id.content);
        listOfComments = findViewById(R.id.listOfComments);
        name = findViewById(R.id.name);
        profilePicture = findViewById(R.id.profilePicture);
        wait = new ProgressDialog(PostActivity.this);
        if (!(UserManger.currentPost.imageUrl.equals("")))
            Glide.with(PostActivity.this).load(UserManger.currentPost.imageUrl).into(profilePicture);

        addComment = findViewById(R.id.addComment);
        commentField = findViewById(R.id.commentField);
        name.setText(UserManger.currentPost.getPostOwnerName());
        content.setText(UserManger.currentPost.getContent());
        List<Comment> comments = UserManger.currentPost.getComments();
        final CommentsAdapter adapter = new CommentsAdapter(PostActivity.this, comments);
        listOfComments.setAdapter(adapter);

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wait.setTitle("Please Wait");
                wait.setMessage("Loading...");
                wait.show();
                if (commentField.getText().toString().equals("")) {
                    return;
                }
                else {
                    Comment comment = new Comment(commentField.getText().toString(), UserManger.currentUser.getUserId(), UserManger.currentUser.getName(), UserManger.currentUser.getUrl());
                    UserManger.currentPost.getComments().add(comment);
                    adapter.notifyDataSetChanged();
                    listOfComments.smoothScrollToPosition(adapter.getCount() - 1);

                    findUsers(comment, adapter);
                    commentField.setText("");
                }
            }
        });

    }

    public void findUsers(final Comment comment, final ArrayAdapter adapter) {

        FirebaseDatabase mFirebaseDatabase;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        UserManger.currentPost.getFollowersID().add(UserManger.currentUser.getUserId());
        final DatabaseReference myRef = mFirebaseDatabase.getReference().child("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = (ds.getValue(User.class));
                    for (int i = 0; i < UserManger.currentCourse.getStudentsId().size(); i++) {
                        if (user.getUserId().equals(UserManger.currentCourse.getStudentsId().get(i))) {
                            updateAndSendNotification(user, comment);
                        }

                    }
                    for (int i = 0; i < UserManger.currentCourse.getInstructorsId().size(); i++) {
                        if (user.getUserId().equals(UserManger.currentCourse.getInstructorsId().get(i))) {
                            updateAndSendNotification(user, comment);
                        }

                    }

                }
                wait.dismiss();
                myRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PostActivity.this, "Faild",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateAndSendNotification(User user, Comment comment) {

        for (int i = 0; i < user.getCourses().size(); i++) {
            if (((Course) (user.getCourses().get(i))).getId() == (UserManger.currentCourse.getId())) {
                Course course = (Course) user.getCourses().get(i);
                for (int j = 0; j < course.getPosts().size(); j++) {
                    Post post = course.getPosts().get(j);
                    if (post.getId() == UserManger.currentPost.getId()) {
                        post.getComments().add(comment);
                    }
                }
            }
        }
        for (int i = 0; i < UserManger.currentPost.getFollowersID().size(); i++) {
            if (user.getUserId().equals(UserManger.currentPost.getFollowersID().get(i))) {
                CommentNotifications commentNotifications=   new CommentNotifications(UserManger.currentUser.getName(), UserManger.currentCourse, UserManger.currentUser.getUserId(), UserManger.currentPost);
                user.getNotifications().add(0,commentNotifications);
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                session = Session.getDefaultInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("piazymanger@gmail.com", "57712150a");
                    }
                });
                RetreiveFeedTask task = new RetreiveFeedTask(session, UserManger.currentCourse.getName(),commentNotifications.getContent(), user.getEmail(), PostActivity.this);
                task.execute();
            }
        }
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user.getUserId()).setValue(user);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(PostActivity.this, CourseActivity.class);
            startActivity(intent);
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }


}
