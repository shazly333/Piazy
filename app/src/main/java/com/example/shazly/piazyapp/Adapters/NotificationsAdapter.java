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
import java.util.List;

/**
 * Created by shazly on 02/02/18.
 */

public class NotificationsAdapter extends ArrayAdapter {


    public NotificationsAdapter(Context c, List<String> notifications){

        super(c,0,notifications);
    }
    public NotificationsAdapter(){

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

        Notification notification = (Notification) getItem(position);
        TextView courseView = (TextView) listItemView.findViewById(R.id.course);
        courseView.setText(notification.getContent());

        return listItemView;
    }
}
