/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author zzz
 */
public class Mentor {
    String Achivement, Description, MentorStatus, fullname, Avatar;
    int id, CvID;
    int follow;
    float rate;
    int ratingTime;
    String account;

    public Mentor(String MentorStatus, String Achivement, String Description, int id, int CvID, String fullname, String Avatar) {
        this.Avatar = Avatar;
        this.fullname = fullname;
        this.MentorStatus = MentorStatus;
        this.Achivement = Achivement;
        this.Description = Description;
        this.id = id;
        this.CvID = CvID;
    }
    
    public Mentor() {
        
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getRatingTime() {
        return ratingTime;
    }

    public void setRatingTime(int ratingTime) {
        this.ratingTime = ratingTime;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }
    
    public String getFullname() {
        return fullname;
    }

    public String getMentorStatus() {
        return MentorStatus;
    }

    public void setMentorStatus(String MentorStatus) {
        this.MentorStatus = MentorStatus;
    }

    public String getAchivement() {
        return Achivement;
    }

    public void setAchivement(String Achivement) {
        this.Achivement = Achivement;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCvID() {
        return CvID;
    }

    public void setCvID(int CvID) {
        this.CvID = CvID;
    }

    @Override
    public String toString() {
        return "Mentor{" + "Achivement=" + Achivement + ", Description=" + Description + ", MentorStatus=" + MentorStatus + ", fullname=" + fullname + ", Avatar=" + Avatar + ", id=" + id + ", CvID=" + CvID + ", follow=" + follow + ", rate=" + rate + ", ratingTime=" + ratingTime + ", account=" + account + '}';
    }
    
}

