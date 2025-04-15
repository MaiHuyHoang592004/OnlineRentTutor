package model;

import java.sql.Timestamp;

public class Report {
    int id;
    int uid;
    User user;
    String fullname;
    String content;
    Timestamp sendTime;
    String status;

    public Report(int uid, String content, Timestamp SendTime, String fullname, String status, int id) {
        this.uid = uid;
        this.content = content;
        this.sendTime = SendTime;
        this.fullname = fullname;
        this.status = status;
        this.id = id;
    }
    
    public Report() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp SendTime) {
        this.sendTime = SendTime;
    }
    
}
