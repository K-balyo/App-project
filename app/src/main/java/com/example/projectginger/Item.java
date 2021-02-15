package com.example.projectginger;

import java.util.Date;

public class Item {

    int itemID;
    String iname;
    String kind;
    int price;
    String area;
    String address;
    int state;
    String image;
    String tag;
    String content;
    Date cDate;
    Date mDate;
    String Latitude;
    String Longitude;
    String fee;
    int itemRival; //찜한사람 수

    public Item(int itemID, String iname, String area, String fee, String kind,int itemRival) {
        this.itemID = itemID;
        this.iname = iname;
        this.area = area;
        this.fee = fee;
        this.kind = kind;
        this.itemRival = itemRival;
    }




    public int getItemRival() {
        return itemRival;
    }

    public void setItemRival(int itemRival) {
        this.itemRival = itemRival;
    }

    public Item(int itemID, String iname, String kind, int price, String area, String address, int state, String image, String tag, String content, Date cDate, Date mDate, String latitude, String longitude, String fee) {
        this.itemID = itemID;
        this.iname = iname;
        this.kind = kind;
        this.price = price;
        this.area = area;
        this.address = address;
        this.state = state;
        this.image = image;
        this.tag = tag;
        this.content = content;
        this.cDate = cDate;
        this.mDate = mDate;
        Latitude = latitude;
        Longitude = longitude;
        this.fee = fee;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
