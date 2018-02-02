package com.example.shazly.piazyapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shazly.piazyapp.Activity.PostActivity;
import com.example.shazly.piazyapp.Adapters.PostsAdapter;
import com.example.shazly.piazyapp.Model.Post;
import com.example.shazly.piazyapp.Model.User;
import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.R;

import java.util.List;

/**
 * Created by shazly on 02/02/18.
 */

public class FragmentPosts extends Fragment{


    User user;
    ListView listOfPosts;
    TextView emptyPosts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_posts, container, false);
        listOfPosts = ((ListView) view.findViewById(R.id.listOfPosts));
        emptyPosts = view.findViewById(R.id.emptyPosts);
        setHasOptionsMenu(true);
        showData();
        return view;
    }


    private void showData() {
        List<Post> posts = UserManger.currentCourse.getPosts();
        if(posts.size() != 0) {
            final PostsAdapter adapter = new PostsAdapter(getActivity(), posts);
            listOfPosts.setAdapter(adapter);
            listOfPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Post post = (Post) adapter.getItem(position);
                    UserManger.currentPost = post;
                    Intent intent = new Intent(getActivity(), PostActivity.class);
                    startActivity(intent);
                }
            });
        }
        else
            emptyPosts.setVisibility(View.VISIBLE);

    }
}
