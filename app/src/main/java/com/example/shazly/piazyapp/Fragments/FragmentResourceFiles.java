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

import com.example.shazly.piazyapp.AddResourceFileActivity;
import com.example.shazly.piazyapp.Adapters.ResourceFilesAdapter;
import com.example.shazly.piazyapp.Model.ResourceFiles;
import com.example.shazly.piazyapp.Model.User;
import com.example.shazly.piazyapp.Model.UserManger;
import com.example.shazly.piazyapp.R;

import java.util.List;

/**
 * Created by shazly on 02/02/18.
 */

public class FragmentResourceFiles extends Fragment{

        User user;
        ListView listOfFiles;
        TextView noFiles;
     private FloatingActionButton newFile;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_resource_files, container, false);
        listOfFiles = ((ListView) view.findViewById(R.id.listOfSourceFiles));
        noFiles = view.findViewById(R.id.emptyFiles);
            newFile =((FloatingActionButton) view.findViewById(R.id.addSourceFile));
            if (!isInstructor(UserManger.currentUser.getUserId())) {
                newFile.setVisibility(View.INVISIBLE);
            }

            newFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AddResourceFileActivity.class);
                    startActivity(intent);
                }
            });
        setHasOptionsMenu(true);
        showData();
        return view;
    }


    private void showData() {
        List<ResourceFiles> files = UserManger.currentCourse.getFiles();
        if(files.size() != 0) {
            final ResourceFilesAdapter adapter = new ResourceFilesAdapter(getActivity(), files);
            listOfFiles.setAdapter(adapter);
            listOfFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ResourceFiles file = (ResourceFiles) adapter.getItem(position);
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
    private boolean isInstructor(String id) {

        for (int i = 0; i < UserManger.currentCourse.getInstructorsId().size(); i++) {
            if (UserManger.currentCourse.getInstructorsId().get(i).equals(id))
                return true;

        }
        return false;
    }

}
