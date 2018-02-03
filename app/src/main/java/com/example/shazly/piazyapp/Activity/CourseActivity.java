package com.example.shazly.piazyapp.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shazly.piazyapp.Adapters.CourseSectionPageAdapter;
import com.example.shazly.piazyapp.Adapters.HomeSectionPageAdapter;
import com.example.shazly.piazyapp.Adapters.PostsAdapter;
import com.example.shazly.piazyapp.Fragments.FragmentCourses;
import com.example.shazly.piazyapp.Fragments.FragmentNotifications;
import com.example.shazly.piazyapp.Fragments.FragmentPosts;
import com.example.shazly.piazyapp.Model.Post;
import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class CourseActivity extends AppCompatActivity {


    private CourseSectionPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
  //  private FloatingActionButton newPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
       // newPost = findViewById(R.id.newPost);
        mSectionsPageAdapter = new CourseSectionPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
//        newPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CourseActivity.this, AddPostActivity.class);
//                startActivity(intent);
//            }
//        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isInstructor(UserManger.currentUser.getUserId()))
            getMenuInflater().inflate(R.menu.menu_home, menu);

        return true;
    }

    private boolean isInstructor(String id) {

        for (int i = 0; i < UserManger.currentCourse.getInstructorsId().size(); i++) {
            if (UserManger.currentCourse.getInstructorsId().get(i).equals(id))
                return true;

        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signOut) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(CourseActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager viewPager) {
        HomeSectionPageAdapter adapter = new HomeSectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentPosts(), "Posts");
        adapter.addFragment(new FragmentNotifications(), "Notifications");
        viewPager.setAdapter(adapter);
    }


}
