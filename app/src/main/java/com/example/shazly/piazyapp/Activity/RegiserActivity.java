package com.example.shazly.piazyapp.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.R;
import com.example.shazly.piazyapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegiserActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    String mail;
    String password;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiser);
        Button bt = findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickk(v);
            }
        });

    }


    public void onClickk(View v) {
        mail = ((EditText) findViewById(R.id.email)).getText().toString();
        password = ((EditText) findViewById(R.id.pass)).getText().toString();
        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            User user1 = new User(user, ((EditText) findViewById(R.id.name)).getText().toString());
                            mDatabase.child("users").child(user.getUid()).setValue(user1);
                            UserManger.currentUser = user1;
                            Intent intent = new Intent(RegiserActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegiserActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}
