package com.example.shazly.piazyapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shazly.piazyapp.R;

public class PostActivity extends AppCompatActivity {

    ListView listOfComments;
    TextView content;
    TextView name;
    ImageView profilePicture;
    ImageView addComment;
    EditText commentField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        content = findViewById(R.id.content);
        listOfComments = findViewById(R.id.listOfPosts);
        name = findViewById(R.id.name);
        profilePicture = findViewById(R.id.profilePicture);
        addComment = findViewById(R.id.addComment);
        commentField = findViewById(R.id.commentField);


    }





}
