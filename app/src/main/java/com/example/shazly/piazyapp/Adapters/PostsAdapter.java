package com.example.shazly.piazyapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shazly.piazyapp.Model.Post;
import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.R;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends ArrayAdapter {

    public PostsAdapter(Context c, List<Post> posts){

        super(c,0,posts);
    }
    public PostsAdapter(){

        super(null,0,0);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_list_posts, parent, false);
        }
        UserManger userManger = new UserManger();
        Post post = (Post) getItem(position);
        TextView postTitle = (TextView) listItemView.findViewById(R.id.title);
        postTitle.setText(post.getTitle());
        TextView madeBy = (TextView) listItemView.findViewById(R.id.madeBy);
        madeBy.setText(post.getPostOwnerName());
        return listItemView;
    }

}
