package com.example.shazly.piazyapp.Model;

/**
 * Created by shazly on 02/02/18.
 */

public class ResourceFiles {
    String title="";
    String path= "";

    public ResourceFiles() {
    }

    public ResourceFiles(String title, String path) {
        this.title = title;
        this.path = path;
    }

    public String getTitle() {

        return title;
    }

    public String getPath() {
        return path;
    }
}
