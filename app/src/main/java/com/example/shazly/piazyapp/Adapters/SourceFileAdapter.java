package com.example.shazly.piazyapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shazly.piazyapp.Model.Post;
import com.example.shazly.piazyapp.Model.SourceFiles;
import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.R;

import java.util.List;

/**
 * Created by shazly on 02/02/18.
 */

public class SourceFileAdapter extends ArrayAdapter {

    public SourceFileAdapter(Context c, List<SourceFiles> files) {

        super(c, 0, files);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_list_courses, parent, false);
        }
        UserManger userManger = new UserManger();
        SourceFiles file = (SourceFiles) getItem(position);
        TextView fileTitle = (TextView) listItemView.findViewById(R.id.course);
        fileTitle.setText(file.getTitle());
        return listItemView;
    }
}
