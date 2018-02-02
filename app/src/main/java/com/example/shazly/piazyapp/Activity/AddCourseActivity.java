package com.example.shazly.piazyapp.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.CourseBuilder;
import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.Model.User;
import com.example.shazly.piazyapp.Notifications.AddToCourseNotification;
import com.example.shazly.piazyapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddCourseActivity extends AppCompatActivity {

    EditText courseNameField;
    EditText courseCodeField;
    EditText facultyField;
    String name;
    String code;
    String faculty;
    ArrayList<String> students = new ArrayList<>();
    ArrayList<String> instructors = new ArrayList<>();
    ImageButton addStudentButton;
    ImageButton addInstructorbutton;
    TextView addingInstructorState;
    TextView addingStudentState;
    boolean firstUpdate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        courseNameField = findViewById(R.id.courseName);
        facultyField = findViewById(R.id.faculty);
        courseCodeField = findViewById(R.id.courseCode);
        addInstructorbutton = findViewById(R.id.addInstructor);
        addStudentButton = findViewById( R.id.addStudent);
        addingInstructorState = findViewById(R.id.addingInstructorState);
        addingStudentState = findViewById(R.id.addingStudentState);
        instructors.add(UserManger.currentUser.getUserId());
        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingStudentState.setText("Waiting");
                try {
                    addFollower(((EditText) (findViewById(R.id.studentEmail))), students, addingStudentState);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addingInstructorState.setText("");
            }
        });
        addInstructorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingInstructorState.setText("Waiting");
                try {
                    addFollower(((EditText) (findViewById(R.id.instructorEmail))), instructors, addingInstructorState);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addingStudentState.setText("");
            }
        });
    }

    private void addFollower(EditText email, ArrayList<String> followers, TextView state) throws InterruptedException {
       email.setEnabled(false);
        UserManger userManger = new UserManger();
       userManger.FindUserByEmail(email, followers, state);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_new_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.done_action) {
            if (name == "" || faculty == "" || code == "") {
                Toast.makeText(AddCourseActivity.this, "You Should Fill All Data",
                        Toast.LENGTH_SHORT).show();

            } else {
                name = courseNameField.getText().toString();
                code = courseCodeField.getText().toString();
                faculty = facultyField.getText().toString();
                CourseBuilder builder = CourseBuilder.getInstance(name, code, students, instructors);
                Course course = builder.buildCourse();
                UserManger.currentUser.getCourses().add(course);
                UserManger.currentUser.update();
                try {
                    addCourseToFollower(instructors, course);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    addCourseToFollower(students, course);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                UserManger.currentCourse = course;
                Intent intent = new Intent(AddCourseActivity.this, HomeActivity.class);
                startActivity(intent);
                return true;
            }
        } else if (id == R.id.cancel_action) {
            Intent intent = new Intent(AddCourseActivity.this, HomeActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addCourseToFollower(ArrayList<String> followers, Course course) throws InterruptedException {
        for(int i = 0; i < followers.size(); i++)
           findUserByID(followers.get(i), course);
    }
    public void findUserByID(final String id, final Course course) {

         FirebaseDatabase mFirebaseDatabase;
        FirebaseAuth mAuth;
         FirebaseAuth.AuthStateListener mAuthListener;
          ;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user1 = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
       final DatabaseReference  myRef = mFirebaseDatabase.getReference().child("users/" + id + "/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              User  user = (dataSnapshot.getValue(User.class));
                if (!(id.equals( UserManger.currentUser.getUserId())) && firstUpdate) {
                    user.getCourses().add(course);
                    user.getNotifications().add(new AddToCourseNotification(UserManger.currentUser, course));

                    user.update();
                    Toast.makeText(AddCourseActivity.this, "AddNotifications",
                            Toast.LENGTH_SHORT).show();
                    firstUpdate = false;

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}
