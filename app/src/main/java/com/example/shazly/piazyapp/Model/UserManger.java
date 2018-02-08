package com.example.shazly.piazyapp.Model;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shazly.piazyapp.Activity.HomeActivity;
import com.example.shazly.piazyapp.Activity.Wait;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by shazly on 01/02/18.
 */

public class UserManger  {
    public static User currentUser;
    public static Course currentCourse;
    public static Post currentPost;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private User user = new User();
    private DatabaseReference mDatabase;
    boolean x = true;

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


    public void FindUserByEmail(final EditText addedEmail, final ArrayList<String> followers, final TextView state, final ImageButton addButton) throws InterruptedException {
        myRef = mFirebaseDatabase.getReference().child("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = null;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String email = ds.child("email").getValue(String.class);
                        if (email.equals(addedEmail.getText().toString())) {
                            user = ds.getValue(User.class);
                        }
                    }
                    if (user != null) {
                        if (thisUserHasBeenAddedBefore(user, followers)) {
                            state.setText("Has Been Added Before As Instructor Or Student!!");
                        } else {
                            followers.add(user.getUserId());
                            state.setText("Done!");
                        }
                    } else {
                        state.setText("Not Found!!");
                    }
                    addedEmail.setEnabled(true);
                    addedEmail.setText("");
                    addButton.setEnabled(true);
                    myRef.removeEventListener(this);

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

    public User findCurrentUser(String id, final Context context, final Intent intent, final ProgressDialog wait) throws InterruptedException {

        myRef = mFirebaseDatabase.getReference().child("users/" + id + "/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = (dataSnapshot.getValue(User.class));
                currentUser = user;
                context.startActivity(intent);
                wait.dismiss();
                myRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return user;

    }

    public void updateUser(User user) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user.getUserId()).setValue(user);
    }

}
