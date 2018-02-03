package com.example.shazly.piazyapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shazly.piazyapp.Model.User;
import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Wait extends AppCompatActivity implements ValueEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
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
