package com.example.shazly.piazyapp.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.shazly.piazyapp.Fragments.FragmentCourses;
import com.example.shazly.piazyapp.Fragments.FragmentNotifications;
import com.example.shazly.piazyapp.R;
import com.example.shazly.piazyapp.Adapters.SectionPageAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {


    private SectionPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    private FloatingActionButton newCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Log.d(TAG, "onCreate: Starting.");
        newCourse = findViewById(R.id.newCourse);
        mSectionsPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        newCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddCourseActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signOut) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentCourses(), "Courses");
       adapter.addFragment(new FragmentNotifications(), "Notifications");
//        adapter.addFragment(new fragment3(), "TAB3");
        viewPager.setAdapter(adapter);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }


}
