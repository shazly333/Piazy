package com.example.shazly.piazyapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

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
    ImageButton addInstructorButton;
    TextView addingInstructorState;
    TextView addingStudentState;
    ProgressDialog wait;
Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        courseNameField = findViewById(R.id.courseName);
        facultyField = findViewById(R.id.faculty);
        courseCodeField = findViewById(R.id.courseCode);
        addInstructorButton = findViewById(R.id.addInstructor);
        addStudentButton = findViewById( R.id.addStudent);
        addingInstructorState = findViewById(R.id.addingInstructorState);
        addingStudentState = findViewById(R.id.addingStudentState);
        wait = new ProgressDialog(AddCourseActivity.this);;
        instructors.add(UserManger.currentUser.getUserId());
        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingStudentState.setText("Waiting");
                try {
                    addStudentButton.setEnabled(false);
                    addFollower(((EditText) (findViewById(R.id.studentEmail))), students, addingStudentState, addStudentButton);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addingInstructorState.setText("");
            }
        });
        addInstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingInstructorState.setText("Waiting");
                try {
                    addInstructorButton.setEnabled(false);
                    addFollower(((EditText) (findViewById(R.id.instructorEmail))), instructors, addingInstructorState, addInstructorButton);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addingStudentState.setText("");
            }
        });
    }

    private void addFollower(EditText email, ArrayList<String> followers, TextView state, ImageButton addButton) throws InterruptedException {
       email.setEnabled(false);
        UserManger userManger = new UserManger();
       userManger.FindUserByEmail(email, followers, state, addButton);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_new_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.done_action) {
            name = courseNameField.getText().toString();
            code = courseCodeField.getText().toString();
            faculty = facultyField.getText().toString();
            if (name.equals("")|| faculty.equals( "") || code .equals( "")) {
                Toast.makeText(AddCourseActivity.this, "You Should Fill All Data",
                        Toast.LENGTH_SHORT).show();

            } else {

                CourseBuilder builder = new  CourseBuilder(name, code, students, instructors);
                Course course = builder.buildCourse();
                wait.setTitle("Please Wait");
                wait.setMessage("Loading...");
                wait.show();
                findCourseID(course);
                return true;
            }
        } else if (id == R.id.cancel_action) {
            Intent intent = new Intent(AddCourseActivity.this, HomeActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void AddCourseToFollowers(final Course course) {
         FirebaseDatabase mFirebaseDatabase;
        FirebaseAuth mAuth;
         FirebaseAuth.AuthStateListener mAuthListener;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user1 = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
       final DatabaseReference  myRef = mFirebaseDatabase.getReference().child("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = (ds.getValue(User.class));
                    for(int i = 0; i < students.size(); i++) {
                        if(user.getUserId().equals(students.get(i))) {
                            updateAndSendNotification(user, course);
                        }

                    }
                        for(int i = 0; i < instructors.size(); i++) {
                            if(user.getUserId().equals(instructors.get(i))) {
                                updateAndSendNotification(user, course);
                            }

                        }
                    }
                    wait.dismiss();

                Intent intent = new Intent(AddCourseActivity.this, HomeActivity.class);
                startActivity(intent);
                    myRef.removeEventListener(this);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    public void updateAndSendNotification(User user, Course course) {

        user.getCourses().add(0,course);
       AddToCourseNotification addToCourseNotification = new AddToCourseNotification(UserManger.currentUser.getName(), course, UserManger.currentUser.getUserId());
        user.getNotifications().add(0, addToCourseNotification);
        user.update();
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
        RetreiveFeedTask task = new RetreiveFeedTask(session, UserManger.currentCourse.getName(),addToCourseNotification.getContent(), user.getEmail(), AddCourseActivity.this);
        task.execute();

    }
    public void findCourseID(final Course course) {

        FirebaseDatabase mFirebaseDatabase;
        FirebaseAuth mAuth;
        FirebaseAuth.AuthStateListener mAuthListener;
        final DatabaseReference  mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user1 = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = mFirebaseDatabase.getReference().child("LastCourseId");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    course.id = dataSnapshot.getValue(int.class);
                    mDatabase.child("LastCourseId").setValue(course.id + 1);
                    UserManger.currentUser.getCourses().add(course);
                    AddCourseToFollowers(course);
                    UserManger.currentCourse = course;


                myRef.removeEventListener(this);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}
