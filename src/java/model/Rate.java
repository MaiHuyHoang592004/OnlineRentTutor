package model;

import java.sql.Timestamp;


public class Rate {
    int senderID, mentorID;
    int noStar;
    String senderName, senderAvatar;
    String content;
    Timestamp rateTime;
    int requestID;

    public Rate(int senderID, int mentorID, int noStar, String senderName, String senderAvatar, String content, Timestamp rateTime, int requestID) {
        this.senderID = senderID;
        this.mentorID = mentorID;
        this.noStar = noStar;
        this.senderName = senderName;
        this.senderAvatar = senderAvatar;
        this.content = content;
        this.rateTime = rateTime;
        this.requestID = requestID;
    }

    public Rate() {
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public int getNoStar() {
        return noStar;
    }

    public void setNoStar(int noStar) {
        this.noStar = noStar;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getRateTime() {
        return rateTime;
    }

    public void setRateTime(Timestamp rateTime) {
        this.rateTime = rateTime;
    }

    @Override
    public String toString() {
        return "Rate{" + "senderID=" + senderID + ", mentorID=" + mentorID + ", noStar=" + noStar + ", senderName=" + senderName + ", senderAvatar=" + senderAvatar + ", content=" + content + ", rateTime=" + rateTime + ", requestID=" + requestID + '}';
    }
    
}
