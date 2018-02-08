package com.example.shazly.piazyapp.Activity;

import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;
    Button bt;
    public static ProgressDialog wait ;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        wait =     new ProgressDialog(LoginActivity.this);
        mEmailField = findViewById(R.id.email);
        mPasswordField = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        bt = findViewById(R.id.signIn);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
onClickk(v);
            }
        });
        Button registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegiserActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void onClickk(View v) {

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        if(email.equals("")  || password.equals("")) {

            Toast.makeText(LoginActivity.this, "Write Your Email And Password",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        else
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserManger userManger = new UserManger();
                            try {
                                wait.setTitle("Please Wait");
                                wait.setMessage("Loading...");
                                wait.show();
                                UserManger.currentUser= userManger.findCurrentUser(user.getUid().toString(), LoginActivity.this, new Intent(LoginActivity.this, HomeActivity.class), wait);


                                int g = 1;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }
    public  void display() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}

