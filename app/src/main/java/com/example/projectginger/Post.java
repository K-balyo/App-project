package com.example.projectginger;

import java.util.Date;

public class Post {
    int postID;
    String userID;
    String title;
    String area;
    int state;
    String image;
    String content;
    Date CRE_DATE;
    Date MOD_DATE;
    String sc;
    int itemRival;

    public int getItemRival() {
        return itemRival;
    }

    public void setItemRival(int itemRival) {
        this.itemRival = itemRival;
    }
    public Post(int postID, String userID, String title, String area, int state, String content, Date CRE_DATE, Date MOD_DATE, int itemRival) {
        this.postID = postID;
        this.userID = userID;
        this.title = title;
        this.area = area;
        this.state = state;
        this.content = content;
        this.CRE_DATE = CRE_DATE;
        this.MOD_DATE = MOD_DATE;
        this.itemRival = itemRival;
    }

    public Post(int postID, String userID, String title, String area, String sc, String content, Date CRE_DATE, Date MOD_DATE) {
        this.postID = postID;
        this.userID = userID;
        this.title = title;
        this.area = area;
        this.sc = sc;
        this.content = content;
        this.CRE_DATE = CRE_DATE;
        this.MOD_DATE = MOD_DATE;
    }
    public Post(int postID, String userID, String title, String content, String area, int state) {
        this.postID = postID;
        this.userID = userID;
        this.title = title;
        this.content = content;
        this.area = area;
        this.state = state;

    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public Post(int postID, String userID, String title, String area, int state, String image, String content, Date CRE_DATE, Date MOD_DATE) {
        this.postID = postID;
        this.userID = userID;
        this.title = title;
        this.area = area;
        this.state = state;
        this.image = image;
        this.content = content;
        this.CRE_DATE = CRE_DATE;
        this.MOD_DATE = MOD_DATE;
    }

    public Post(int postID, String userID, String title, String area, int state, String content, Date cre_date, Date mod_date) {
        this.postID = postID;
        this.userID = userID;
        this.title = title;
        this.area = area;
        this.state = state;
        this.content = content;
        this.CRE_DATE = CRE_DATE;
        this.MOD_DATE = MOD_DATE;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCRE_DATE() {
        return CRE_DATE;
    }

    public void setCRE_DATE(Date CRE_DATE) {
        this.CRE_DATE = CRE_DATE;
    }

    public Date getMOD_DATE() {
        return MOD_DATE;
    }

    public void setMOD_DATE(Date MOD_DATE) {
        this.MOD_DATE = MOD_DATE;
    }


    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
}
