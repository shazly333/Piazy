package com.example.shazly.piazyapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.service.textservice.SpellCheckerService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.Post;
import com.example.shazly.piazyapp.Model.PostBuilder;
import com.example.shazly.piazyapp.Model.User;
import com.example.shazly.piazyapp.Model.UserManger;
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

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class AddPostActivity extends AppCompatActivity {

    EditText titleField;
    EditText contentField;
    String title;
    String content;
    List<String> followers = new ArrayList<String>();
    ProgressDialog wait;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        titleField = findViewById(R.id.title);
        contentField = findViewById(R.id.content);
        wait = new ProgressDialog(AddPostActivity.this);
        followers.add(UserManger.currentUser.getUserId());
        for (int i = 0; i < UserManger.currentCourse.getInstructorsId().size(); i++) {
            if (!(UserManger.currentUser.getUserId().equals(UserManger.currentCourse.getInstructorsId().get(i))))
                ;
            followers.add(UserManger.currentCourse.getInstructorsId().get(i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.done_action) {
            if (titleField.getText().toString().equals("") || titleField.getText().toString().equals("")) {
                Toast.makeText(AddPostActivity.this, "You Should Fill Title and Content",
                        Toast.LENGTH_SHORT).show();

            } else {
                title = titleField.getText().toString();
                content = contentField.getText().toString();
                PostBuilder builder = new PostBuilder(content, title, followers);
                Post post = builder.buildPost();
                post.imageUrl = UserManger.currentUser.url;
                wait.setTitle("Please Wait");
                wait.setMessage("Loading...");
                wait.show();
                post.setItsOwnerHasPicture(UserManger.currentUser.hasPicture());
                findPostID(post);

                return true;
            }
        } else if (id == R.id.cancel_action) {
            Intent intent = new Intent(AddPostActivity.this, CourseActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void findUsers(final Post post) {

        FirebaseDatabase mFirebaseDatabase;
        FirebaseAuth mAuth;
        FirebaseAuth.AuthStateListener mAuthListener;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user1 = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = mFirebaseDatabase.getReference().child("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = (ds.getValue(User.class));
                    for (int i = 0; i < UserManger.currentCourse.getStudentsId().size(); i++) {
                        if (user.getUserId().equals(UserManger.currentCourse.getStudentsId().get(i))) {
                            updateAndSendNotification(user, post);
                        }

                    }
                    for (int i = 0; i < UserManger.currentCourse.getInstructorsId().size(); i++) {
                        if (user.getUserId().equals(UserManger.currentCourse.getInstructorsId().get(i))) {
                            updateAndSendNotification(user, post);
                        }

                    }
                    Intent intent = new Intent(AddPostActivity.this, PostActivity.class);
                    startActivity(intent);
                    wait.dismiss();
                    myRef.removeEventListener(this);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void updateAndSendNotification(User user, Post post) {

        for (int i = 0; i < user.getCourses().size(); i++) {
            if (((Course) (user.getCourses().get(i))).getId() == (UserManger.currentCourse.getId())) {
                ((Course) (user.getCourses().get(i))).getPosts().add(post);
               PostNotification postNotification = new PostNotification(UserManger.currentUser.getName(), UserManger.currentUser.getUserId(), UserManger.currentCourse);
                user.getNotifications().add(0, postNotification);
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
                RetreiveFeedTask task = new RetreiveFeedTask(session, UserManger.currentCourse.getName(),postNotification.getContent(), user.getEmail(), AddPostActivity.this);
                task.execute();

            }
        }
        user.update();

    }


    public void findPostID(final Post post) {

        FirebaseDatabase mFirebaseDatabase;
        FirebaseAuth mAuth;
        FirebaseAuth.AuthStateListener mAuthListener;
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user1 = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = mFirebaseDatabase.getReference().child("LastPostId/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post.id = dataSnapshot.getValue(int.class);
                mDatabase.child("LastPostId").setValue(post.id + 1);
                UserManger.currentCourse.getPosts().add(post);
                findUsers(post);
                UserManger.currentPost = post;
                Wait.postActivity = true;

                myRef.removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}
