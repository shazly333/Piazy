package com.example.shazly.piazyapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.shazly.piazyapp.Activity.CourseActivity;
import com.example.shazly.piazyapp.Activity.Wait;
import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.ResourceFiles;
import com.example.shazly.piazyapp.Model.User;
import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.Notifications.AddFileNotification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by shazly on 02/02/18.
 */

public class AddResourceFileActivity extends AppCompatActivity {

    EditText titleField;
    EditText URLField;
    Context context;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resource_file);
        titleField = findViewById(R.id.title);
        URLField = findViewById(R.id.Url);
        context = AddResourceFileActivity.this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_new_resource_file, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.done_action) {
            ResourceFiles file = new ResourceFiles(titleField.getText().toString(), URLField.getText().toString());
            UserManger.currentCourse.getFiles().add(file);
            findUsers(file);
        }
        else {
            Intent intent = new Intent(context, CourseActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }

    public void findUsers(final ResourceFiles file) {

        FirebaseDatabase mFirebaseDatabase;
        FirebaseAuth mAuth;
        FirebaseAuth.AuthStateListener mAuthListener;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user1 = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = mFirebaseDatabase.getReference().child("users");
        Intent intent = new Intent(AddResourceFileActivity.this, Wait.class);
        startActivity(intent);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = (ds.getValue(User.class));
                    for (int i = 0; i < UserManger.currentCourse.getStudentsId().size(); i++) {
                        if (user.getUserId().equals(UserManger.currentCourse.getStudentsId().get(i))) {
                            updateAndSendNotification(user, file);
                        }

                    }
                    for (int i = 0; i < UserManger.currentCourse.getInstructorsId().size(); i++) {
                        if (user.getUserId().equals(UserManger.currentCourse.getInstructorsId().get(i))) {
                            updateAndSendNotification(user, file);
                        }

                    }
                }
                myRef.removeEventListener(this);
                Intent intent = new Intent(context, CourseActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    public void updateAndSendNotification(User user, ResourceFiles file) {

        for(int i = 0; i < user.getCourses().size(); i++) {
            if(((Course)(user.getCourses().get(i))).getId() == (UserManger.currentCourse.getId())){
                ((Course)(user.getCourses().get(i))).getFiles().add(file);
                user.getNotifications().add(0,new AddFileNotification(file.getTitle(), file.getPath(), UserManger.currentUser.getName(), UserManger.currentCourse, UserManger.currentUser.getUserId()));
            }
        }
        user.update();

    }
}
