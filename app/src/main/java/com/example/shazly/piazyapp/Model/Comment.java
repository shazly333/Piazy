package com.example.shazly.piazyapp.Model;

/**
 * Created by shazly on 03/02/18.
 */

public class Comment {
    String content;
    String ownerId;
    String ownerName;

    public Comment() {
    }

    public Comment(String content, String ownerId, String ownerName) {
        this.content = content;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getContent() {
        return content;
    }

    public String getOwnerId() {
        return ownerId;
    }
}
