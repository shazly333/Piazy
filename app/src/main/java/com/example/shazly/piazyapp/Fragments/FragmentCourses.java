package com.example.shazly.piazyapp.Fragments;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shazly.piazyapp.Activity.CourseActivity;
import com.example.shazly.piazyapp.Activity.Wait;
import com.example.shazly.piazyapp.Adapters.CoursesAdapter;
import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.R;
import com.example.shazly.piazyapp.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shazly on 27/01/18.
 */

public class FragmentCourses extends Fragment {
    private static final String TAG = "Tab1Fragment";
    User user;
    ListView listView;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_courses, container, false);
        listView = ((ListView) view.findViewById(R.id.listOfCourses));
        textView = view.findViewById(R.id.emptyCourses);
        setHasOptionsMenu(true);
        displayData();
        return view;
    }


    private void displayData() {
        UserManger userManger = new UserManger();

        List<Course> courses = UserManger.currentUser.getCourses();
        if (courses.size() != 00) {
            final CoursesAdapter adapter = new CoursesAdapter(getActivity(), (ArrayList<Course>) courses);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Course course = (Course) adapter.getItem(position);
                    UserManger.currentCourse = course;
                    Intent intent = new Intent(getActivity(), CourseActivity.class);
                    startActivity(intent);
//                    try {
//                        //Intent intents = new Intent(getActivity(), Wait.class);
//                        startActivity(intent);
//
//                      //  userManger.findUserByID(UserManger.currentUser.getUserId(), getContext(), intent);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                //    startActivity(intent);
                }
            });
        } else
            textView.setVisibility(View.VISIBLE);

    }
}


