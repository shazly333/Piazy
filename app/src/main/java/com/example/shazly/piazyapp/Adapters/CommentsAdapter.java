package com.example.shazly.piazyapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shazly.piazyapp.Activity.PostActivity;
import com.example.shazly.piazyapp.Activity.Wait;
import com.example.shazly.piazyapp.Model.Comment;
import com.example.shazly.piazyapp.Model.Course;
import com.example.shazly.piazyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shazly on 03/02/18.
 */

public class CommentsAdapter extends ArrayAdapter {

   Context context;
    public CommentsAdapter(Context c, List<Comment> comments){

        super(c,0,comments);
        context = c;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_list_of_comments, parent, false);
        }
        Comment comment = (Comment) getItem(position);
        TextView commentView = (TextView) listItemView.findViewById(R.id.comment);
        commentView.setText(comment.getContent());

        TextView nameView = (TextView) listItemView.findViewById(R.id.name);
        nameView.setText(comment.getOwnerName());
        ImageView img = (ImageView) listItemView.findViewById(R.id.profilePicture);
        if(!(((Comment)getItem(position)).url.equals(""))) {

            Glide.with(context).load(((Comment) getItem(position)).getUrl()).into(img);
        }

        return listItemView;
    }
}
