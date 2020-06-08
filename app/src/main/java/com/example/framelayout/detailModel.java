package com.example.framelayout;

public class detailModel {
private  String name,lastmsg,person;
private int img;
private boolean Expandable;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public boolean isExpandable() {
        return Expandable;
    }

    public void setExpandable(boolean expandable) {
        Expandable = expandable;
    }

    public detailModel(String person,String name, String lastmsg, int img) {
        this.name = name;
        this.lastmsg = lastmsg;
        this.img = img;
        this.person=person;
        this.Expandable=false;
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}

