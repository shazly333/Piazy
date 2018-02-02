package com.example.shazly.piazyapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shazly.piazyapp.Activity.CourseActivity;
import com.example.shazly.piazyapp.Adapters.NotificationsAdapter;
import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.Notifications.Notification;
import com.example.shazly.piazyapp.Model.Post;
import com.example.shazly.piazyapp.R;
import com.example.shazly.piazyapp.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shazly on 31/01/18.
 */

public class FragmentNotifications extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_notifications, container, false);
        ArrayList<String> notifications = new ArrayList<>();
        for(int i = 0; i < UserManger.currentUser.getNotifications().size(); i++)
            notifications.add(((Notification)UserManger.currentUser.getNotifications().get(i)).getContent());
        if (notifications.size() != 0) {
            final NotificationsAdapter adapter = new NotificationsAdapter(getActivity(), notifications);
            ListView listView = view.findViewById(R.id.listOfPosts);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    UserManger.currentCourse = (Course) adapter.getItem(position);
                    Intent intent = new Intent(getActivity(), CourseActivity.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
            TextView textView = view.findViewById(R.id.noPost);
                    textView.setVisibility(View.VISIBLE);
        }
        return view;
    }




}
