package com.example.shazly.piazyapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Notifications.Notification;
import com.example.shazly.piazyapp.R;

import java.util.ArrayList;

/**
 * Created by shazly on 02/02/18.
 */

public class NotificationsAdapter extends ArrayAdapter {


    public NotificationsAdapter(Context c, ArrayList<String> notifications){

        super(c,0,notifications);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_list_courses, parent, false);
        }

        String Notification = (String) getItem(position);
        TextView courseView = (TextView) listItemView.findViewById(R.id.course);
        courseView.setText(Notification);

        return listItemView;
    }
}
