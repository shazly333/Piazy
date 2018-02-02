package com.example.shazly.piazyapp.Model;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shazly.piazyapp.Activity.LoginActivity;
import com.example.shazly.piazyapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by shazly on 01/02/18.
 */

public class UserManger {
    boolean isQueryingFinished = false;
    public static User currentUser;
    public static Course currentCourse;
    public static Post currentPost;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private User user = new User();
    private DatabaseReference mDatabase;
    boolean firstChange = true;

    public UserManger() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = mFirebaseDatabase.getReference();
        final FirebaseUser user1 = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

            }
        };
        mAuth.addAuthStateListener(mAuthListener);

    }



    public void FindUserByEmail(final EditText addedEmail, final ArrayList<String> followers, final TextView state) throws InterruptedException {
        myRef = mFirebaseDatabase.getReference().child("users/");
        firstChange = true;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(firstChange) {
                    User user = null;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String email = ds.child("email").getValue(String.class);
                        if (email.equals(addedEmail.getText().toString())) {
                            user = (ds.getValue(User.class));
                        }
                    }
                    if (user != null) {
                        if (thisUserHasBeenAddedBefore(user, followers)) {
                            state.setText("Has Been Added Before As Instructor Or Student!!");
                        } else {
                            followers.add(user.getUserId());
                            state.setText("Done!");
                        }
                    }
                    else {
                        state.setText("Not Found!!");
                    }
                    addedEmail.setEnabled(true);
                    addedEmail.setText("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private boolean thisUserHasBeenAddedBefore(User user, ArrayList<String> followers) {
        for (int i = 0; i < followers.size(); i++)
            if (followers.get(i).equals(user.getUserId()))
                return true;

        return false;
    }

    public User findUserByID(String id) throws InterruptedException {

        myRef = mFirebaseDatabase.getReference().child("users/" + id + "/");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user = (dataSnapshot.getValue(User.class));
                        currentUser = user;
                        return;
                       // currentUser.courses.get(0).name = currentUser.getName();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


        TimeUnit.SECONDS.sleep(7);

        return user;

    }

    public void updateUser(User user) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user.getUserId()).setValue(user);
    }

}
