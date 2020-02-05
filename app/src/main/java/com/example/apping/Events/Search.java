package com.example.apping.Events;

public class Search {
    String title;
    String postImage;
    String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Search(String title, String postImage, String description) {
        this.title = title;
        this.postImage = postImage;
        this.description = description;
    }

    public Search() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }
}

