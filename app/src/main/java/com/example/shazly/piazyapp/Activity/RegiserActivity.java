package com.example.shazly.piazyapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.R;
import com.example.shazly.piazyapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class RegiserActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    String mail;
    String password;
    private FirebaseAuth mAuth;
    Button addPicture;
    ImageView pictureView;
    int Pick_Image_Request = 71;
    Uri filepath = null;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String url;

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
        pictureView = findViewById(R.id.profilePicture);
        addPicture = findViewById(R.id.addPictre);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), Pick_Image_Request);
                }


            }
        });

    }


    public void onClickk(View v) {
        mail = ((EditText) findViewById(R.id.email)).getText().toString();
        password = ((EditText) findViewById(R.id.pass)).getText().toString();
        if(!(((EditText)findViewById(R.id.confirmPassword)).getText().toString().equals(password))) {
            Toast.makeText(RegiserActivity.this, "Password and Confirm Password should be the same",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(RegiserActivity.this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if(filepath != null) {

                                StorageReference rf = storageReference.child("images/"+mAuth.getCurrentUser().getUid());
                                rf.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        url = taskSnapshot.getDownloadUrl().toString();
                                        progressDialog.dismiss();
                                        buildUser();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegiserActivity.this, "Uploading Image failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progressLoad = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                             progressDialog.setMessage("Loading " + (int) progressLoad + "%");
                                    }
                                });

                            }
                            else
                                buildUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegiserActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                });
//
//            <img src="Images/addcourseemail.jpeg" width = "200">
//    <img src="Images/emailcomment.jpeg" width = "200">
//    <img src="Images/instructoremailcomment.jpeg" width = "200">
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Pick_Image_Request && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();
            try {
                pictureView.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), filepath));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void buildUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        User user1 = new User(user, ((EditText) findViewById(R.id.name)).getText().toString());
        if(filepath == null) {
            user1.setHasPicture(false);
        }
        user1.url = url;
        mDatabase.child("users").child(user.getUid()).setValue(user1);
        UserManger.currentUser = user1;
        Toast.makeText(RegiserActivity.this, "Success Register",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegiserActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}
