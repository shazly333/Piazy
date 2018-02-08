package com.example.shazly.piazyapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shazly.piazyapp.Model.ResourceFiles;
import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.R;

import java.util.List;

/**
 * Created by shazly on 02/02/18.
 */

public class ResourceFilesAdapter extends ArrayAdapter {

    public ResourceFilesAdapter(Context c, List<ResourceFiles> files) {

        super(c, 0, files);
    }
    public ResourceFilesAdapter(){

        super(null,0,0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_list_courses, parent, false);
        }
        UserManger userManger = new UserManger();
        ResourceFiles file = (ResourceFiles) getItem(position);
        TextView fileTitle = (TextView) listItemView.findViewById(R.id.course);
        fileTitle.setText(file.getTitle());
        return listItemView;
    }
}
