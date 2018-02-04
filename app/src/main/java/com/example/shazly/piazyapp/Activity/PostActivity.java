package com.example.shazly.piazyapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.shazly.piazyapp.Notifications.CommentNotifications;
import com.example.shazly.piazyapp.Notifications.PostNotification;
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

public class PostActivity extends AppCompatActivity {

    ListView listOfComments;
    TextView content;
    TextView name;
    ImageView profilePicture;
    ImageButton addComment;
    EditText commentField;
    boolean firstUpdate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        content = findViewById(R.id.content);
        listOfComments = findViewById(R.id.listOfComments);
        name = findViewById(R.id.name);
        profilePicture = findViewById(R.id.profilePicture);
        addComment = findViewById(R.id.addComment);
        commentField = findViewById(R.id.commentField);
        name.setText(UserManger.currentPost.getPostOwnerName());
        content.setText(UserManger.currentPost.getContent());
        List<Comment> comments = UserManger.currentPost.getComments();
       final  CommentsAdapter adapter = new CommentsAdapter(PostActivity.this, comments);
        if (comments.size() != 0) {
            listOfComments.setAdapter(adapter);
        }

       addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment(commentField.getText().toString(), UserManger.currentUser.getUserId(), UserManger.currentUser.getName());
                UserManger.currentPost.getComments().add(comment);
                adapter.notifyDataSetChanged();
                listOfComments.smoothScrollToPosition(adapter.getCount() -1);

                findUsers(comment, adapter);
                commentField.setText("");

            }
        });

    }

    public void findUsers(final Comment comment, final ArrayAdapter adapter) {

        FirebaseDatabase mFirebaseDatabase;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
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
                user.getNotifications().add(0,new CommentNotifications(UserManger.currentUser.getName(), UserManger.currentCourse, UserManger.currentUser.getUserId(), UserManger.currentPost));

            }
        }
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user.getUserId()).setValue(user);
    }


}
