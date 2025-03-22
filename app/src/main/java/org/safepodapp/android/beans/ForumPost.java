package org.safepodapp.android.beans;

public class ForumPost {
    private String userid;
    private String body;
    private final String[] tags = {};

    public ForumPost() {
        //    private int upvotes = 0;
        //    private int downvotes = 0;
    }

    public String getId() {
        return userid;
    }

    public void setId(int id) {
        this.userid = "" + id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        StringBuilder tempTagList = new StringBuilder();
        for (String tag : tags) {
            tempTagList.append(tag);
            tempTagList.append(" ");
        }
        return tempTagList.toString();
    }

    public void setTitle(String[] tags) {
        int counter = 0;
        for (String tag : tags)
            tags[counter++] = tag;
    }

//    public int getUpvotes() {
//        return upvotes;
//    }
//
//    public void setUpvotes(int upvotes) {
//        this.upvotes = upvotes;
//    }
//
//    public int getDownvotes() {
//        return downvotes;
//    }
//
//    public void setDownvotes(int downvotes) {
//        this.downvotes = downvotes;
//    }
}