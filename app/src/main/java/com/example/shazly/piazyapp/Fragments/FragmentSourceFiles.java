package com.example.shazly.piazyapp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shazly.piazyapp.Activity.AddPostActivity;
import com.example.shazly.piazyapp.Adapters.SourceFilesAdapter;
import com.example.shazly.piazyapp.Model.SourceFiles;
import com.example.shazly.piazyapp.Model.User;
import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.R;

import java.util.List;

/**
 * Created by shazly on 02/02/18.
 */

public class FragmentSourceFiles extends Fragment{

        User user;
        ListView listOfFiles;
        TextView noFiles;
     private FloatingActionButton newFile;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_source_files, container, false);
        listOfFiles = ((ListView) view.findViewById(R.id.listOfSourceFiles));
        noFiles = view.findViewById(R.id.emptyFiles);
            newFile =((FloatingActionButton) view.findViewById(R.id.addSourceFile));

            newFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AddPostActivity.class);
                    startActivity(intent);
                }
            });
        setHasOptionsMenu(true);
        showData();
        return view;
    }


    private void showData() {
        List<SourceFiles> files = UserManger.currentCourse.getFiles();
        if(files.size() != 0) {
            final SourceFilesAdapter adapter = new SourceFilesAdapter(getActivity(), files);
            listOfFiles.setAdapter(adapter);
            listOfFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SourceFiles file = (SourceFiles) adapter.getItem(position);
                    String url = file.getPath();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        }
        else
            noFiles.setVisibility(View.VISIBLE);

    }

}
