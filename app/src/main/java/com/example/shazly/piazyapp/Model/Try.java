package com.example.shazly.piazyapp.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by shazly on 01/02/18.
 */

public abstract class Try implements ValueEventListener {
    boolean po =false;
    abstract public void onDataChange(DataSnapshot dataSnapshot);


    abstract public void onCancelled(DatabaseError databaseError) ;

    public void p() {
        po = true;
    }
}
