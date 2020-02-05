package com.example.apping.Events;

public class Notifications {

    private String postid;
    private String organisers;
    private String eventDate;
    private boolean ispost;

    public Notifications(String postid, String organisers, String eventDate, boolean ispost) {
        this.postid = postid;
        this.organisers = organisers;
        this.eventDate = eventDate;
        this.ispost = ispost;
    }

    public Notifications() {
    }


    public boolean isIspost() {
        return ispost;
    }

    public void setIspost(boolean ispost) {
        this.ispost = ispost;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
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
}
