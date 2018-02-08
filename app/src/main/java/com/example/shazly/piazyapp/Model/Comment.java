package com.example.shazly.piazyapp.Model;

/**
 * Created by shazly on 03/02/18.
 */

public class Comment {
    String content;
    String ownerId;
    String ownerName;
    public String url="";
    boolean itsOwnerHasPicture = false;

    public Comment() {
    }

    public Comment(String content, String ownerId, String ownerName, String url) {
        this.url = url;
        this.content = content;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

/*    public String getUrl() {
        return Url;
    }
*/

    public String getUrl() {
        return url;
    }

    public boolean isItsOwnerHasPicture() {
        return itsOwnerHasPicture;
    }

    public void setItsOwnerHasPicture(boolean itsOwnerHasPicture) {
        this.itsOwnerHasPicture = itsOwnerHasPicture;
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
