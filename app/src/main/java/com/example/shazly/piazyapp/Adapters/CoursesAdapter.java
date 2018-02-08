package com.example.shazly.piazyapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.R;

import java.util.ArrayList;
import java.util.List;

public class CoursesAdapter extends ArrayAdapter {


    public CoursesAdapter(Context c, ArrayList<Course> courses){

        super(c,0,courses);
    }
    public CoursesAdapter(){

        super(null,0,0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_list_courses, parent, false);
        }

        Course course = (Course) getItem(position);
        TextView courseView = (TextView) listItemView.findViewById(R.id.course);
        courseView.setText(course.getName());

        return listItemView;
    }
}
