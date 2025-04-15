/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author zzz
 */
public class Follow {
    private int followId;
    private int mentorId;
    private int menteeId;
    private String fullname;
    private String avatar;

    public Follow() {
    }

    public Follow(int followId, int mentorId, int menteeId, String fullname, String avatar) {
        this.followId = followId;
        this.mentorId = mentorId;
        this.menteeId = menteeId;
        this.fullname = fullname;
        this.avatar = avatar;
    }

    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public int getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(int menteeId) {
        this.menteeId = menteeId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Follow{" + "followId=" + followId + ", mentorId=" + mentorId + ", menteeId=" + menteeId + ", fullname=" + fullname + ", avatar=" + avatar + '}';
    }
}