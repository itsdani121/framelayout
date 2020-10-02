package com.example.framelayout;

import android.widget.TextView;

import net.alhazmy13.mediapicker.Image.ImagePicker;

public class detailModel  {
String person,name,lastmsg;
int mainImage,recycleImg;
boolean Expand;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }

    public int getMainImage() {
        return mainImage;
    }

    public void setMainImage(int mainImage) {
        this.mainImage = mainImage;
    }

    public int getRecycleImg() {
        return recycleImg;
    }

    public void setRecycleImg(int recycleImg) {
        this.recycleImg = recycleImg;
    }

    public boolean isExpand() {
        return Expand;
    }

    public void setExpand(boolean expand) {
        Expand = expand;
    }

    public detailModel(String person, String name, String lastmsg, int mainImage, int recycleImg) {
        this.person = person;
        this.name = name;
        this.lastmsg = lastmsg;
        this.mainImage = mainImage;
        this.recycleImg = recycleImg;
        this.Expand=false;
    }
}
