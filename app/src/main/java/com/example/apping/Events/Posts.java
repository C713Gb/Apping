package com.example.apping.Events;

public class Posts {

    private String postid;
    private String postImage;
    private String title;
    private String description;
    private String organisers;
    private String eventDate;
    private String publisher;
    private String eventLocationLatitude;
    private String eventLocationLongitude;

    public String getEventLocationLatitude() {
        return eventLocationLatitude;
    }

    public void setEventLocationLatitude(String eventLocationLatitude) {
        this.eventLocationLatitude = eventLocationLatitude;
    }

    public String getEventLocationLongitude() {
        return eventLocationLongitude;
    }

    public void setEventLocationLongitude(String eventLocationLongitude) {
        this.eventLocationLongitude = eventLocationLongitude;
    }

    public String getOrganisers() {
        return organisers;
    }

    public void setOrganisers(String organisers) {
        this.organisers = organisers;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public Posts(String postid, String postImage, String title, String description, String publisher, String eventLocationLatitude, String eventLocationLongitude) {
        this.postid = postid;
        this.postImage = postImage;
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.eventLocationLatitude = eventLocationLatitude;
        this.eventLocationLongitude = eventLocationLongitude;
    }

    public Posts() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
