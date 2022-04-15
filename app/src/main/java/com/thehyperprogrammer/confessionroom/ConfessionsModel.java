package com.thehyperprogrammer.confessionroom;

public class ConfessionsModel {

    String confTitle,confBody,ConfDate,ConfTime,CurrentUser;


    //Empty
    public ConfessionsModel() {
    }

    public ConfessionsModel(String confTitle, String confBody, String confDate, String confTime, String confuser) {
        this.confTitle = confTitle;
        this.confBody = confBody;
        this.ConfDate = confDate;
        this.ConfTime = confTime;
        this.CurrentUser = confuser;
    }

    public String getConfTitle() {
        return confTitle;
    }

    public String getConfBody() {
        return confBody;
    }

    public String getConfDate() {
        return ConfDate;
    }

    public String getConfTime() {
        return ConfTime;
    }

    public String getCurrentUser() {
        return CurrentUser;
    }


    public void setConfTitle(String confTitle) {
        this.confTitle = confTitle;
    }

    public void setConfBody(String confBody) {
        this.confBody = confBody;
    }

    public void setConfDate(String confDate) {
        ConfDate = confDate;
    }

    public void setConfTime(String confTime) {
        ConfTime = confTime;
    }

    public void setCurrentUser(String currentUser) {
        CurrentUser = currentUser;
    }
}
